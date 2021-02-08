package Controllers;

import Contract.Request.SingleIdRequest;
import Contract.Request.TransactionRequest;
import Contract.Response.TransactionResponse;
import Models.Offer;
import Models.User;
import Protocol.BjpResponse;
import Protocol.StatusCode;
import Services.OfferDataService;
import Services.UserDataService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;

public class TransactionController {

    public static BjpResponse ClaimBalance(User user, TransactionRequest request){
        if (user == null) {
            return new BjpResponse(StatusCode.UNAUTHORIZED, false, "Unauthorized user", "");
        }

        Offer offer = OfferDataService.getOfferById(request.getOfferId());

        if(offer == null) {
            return new BjpResponse(StatusCode.NOT_FOUND, false, "No such offer found.", "");
        }

        if (offer.acceptorId != user.id && offer.creatorId != user.id) {
            return new BjpResponse(StatusCode.BAD_REQUEST, false, "You are not owner of offer.", "");
        }

        double dealPrice = request.getCompletePrice();


        if(!offer.endDate.isBefore(LocalDate.now())) {
            return new BjpResponse(StatusCode.BAD_REQUEST, false, "You need to wait one day after due date passes.", "");
        }


        if((user.id == offer.creatorId && offer.isCreatorCheckout) || (user.id == offer.acceptorId && offer.isAcceptorCheckout)){
            return new BjpResponse(StatusCode.BAD_REQUEST, false, "You have already claimed your money back.", "");
        }

        double percentageChange = (dealPrice - offer.price) / offer.price;
        if((user.id == offer.creatorId && offer.shortPosition) || (user.id == offer.acceptorId && !offer.shortPosition)){
            //User is at short position
            percentageChange = -percentageChange;
        }
        double paybackRate = (percentageChange) * offer.leverage + 1;
        if(paybackRate < 0) {
            paybackRate = 0;
        }else if (paybackRate > 2) {
            paybackRate = 2;
        }
        double payback = (offer.price*offer.amount)*paybackRate;

        user.accountBalance += payback;
        UserDataService.updateUser(user);

        if(user.id == offer.creatorId){
            offer.isCreatorCheckout = true;
        } else {
            offer.isAcceptorCheckout = true;
        }
        OfferDataService.updateOffer(offer);

        Gson gson = new GsonBuilder().create();
        TransactionResponse response = new TransactionResponse(request.getOfferId(), payback);

        return new BjpResponse(StatusCode.OK, true, "Transaction is successfully completed.", gson.toJson(response));
    }
}
