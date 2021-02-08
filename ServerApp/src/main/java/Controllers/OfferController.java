package Controllers;

import Contract.Request.PostOfferRequest;
import Contract.Request.SingleIdRequest;
import Contract.Response.CreateResponse;
import Models.Offer;
import Models.User;
import Protocol.BjpResponse;
import Protocol.StatusCode;
import Services.OfferDataService;
import Services.UserDataService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

public class OfferController {

    public static BjpResponse createOffer(User user, PostOfferRequest request) {
        if (user == null) {
            return new BjpResponse(StatusCode.UNAUTHORIZED, false, "Unauthorized user", "");
        }

        Gson gsonBuilder = new GsonBuilder().create();

        if(user.accountBalance < request.price * request.amount){
            return new BjpResponse(StatusCode.OK, false, "Not enough balance.", gsonBuilder.toJson(new CreateResponse(0)));
        }

        user.accountBalance -= (request.price * request.amount);
        UserDataService.updateUser(user);
        Offer offer = new Offer(user.id, request.price, request.stockCode, request.amount, request.endDate, request.leverage, request.shortPosition);
        int offerId = OfferDataService.addOffer(offer);
        return new BjpResponse(StatusCode.OK, true, "Offer successfully created.", gsonBuilder.toJson(new CreateResponse(offerId)));
    }

    public static BjpResponse cancelOffer(User user, SingleIdRequest request) {
        if (user == null) {
            return new BjpResponse(StatusCode.UNAUTHORIZED, false, "Unauthorized user", "");
        }

        Offer offer = OfferDataService.getOfferById(request.id);

        if(offer == null) {
            return new BjpResponse(StatusCode.NOT_FOUND, false, "No such offer found.", "");
        }

        if(user.id != offer.creatorId){
            return new BjpResponse(StatusCode.FORBIDDEN, false, "You cannot do this operation", "");
        }

        if(offer.isAccepted) {
            return new BjpResponse(StatusCode.BAD_REQUEST, false, "You cannot cancel an accepted order", "");
        }

        user.accountBalance += offer.amount * offer.price;
        UserDataService.updateUser(user);
        OfferDataService.deleteOffer(offer);

        return new BjpResponse(StatusCode.OK, true, "Offer successfully cancelled.", "");
    }

    public static BjpResponse acceptOffer(User user, SingleIdRequest request) {
        if (user == null) {
            return new BjpResponse(StatusCode.UNAUTHORIZED, false, "Unauthorized user", "");
        }

        Offer offer = OfferDataService.getOfferById(request.id);

        if(offer == null) {
            return new BjpResponse(StatusCode.NOT_FOUND, false, "No such offer found.", "");
        }

        if (user.id == offer.creatorId){
            return new BjpResponse(StatusCode.BAD_REQUEST, false, "You cannot accept your own offer.", "");
        }

        if (offer.isAccepted){
            return new BjpResponse(StatusCode.BAD_REQUEST, false, "This offer has already been accepted.", "");
        }

        if (!offer.endDate.isAfter(LocalDate.now())){
            return new BjpResponse(StatusCode.BAD_REQUEST, false, "This offer is expired.", "");
        }

        user.accountBalance -= offer.amount * offer.price;
        UserDataService.updateUser(user);
        offer.acceptorId = user.id;
        offer.isAccepted = true;
        OfferDataService.updateOffer(offer);

        return new BjpResponse(StatusCode.OK, true, "Offer is successfully accepted.", "");
    }

    public static BjpResponse getMyOffers(User user) {
        Gson gsonBuilder = new GsonBuilder().create();
        ArrayList<Offer> offers = OfferDataService.getOffersByUserId(user.id);
        return new BjpResponse(StatusCode.OK, true, "Successfully Retrieved", gsonBuilder.toJson(offers));
    }

    public static BjpResponse getAvailableOffers(User user){

        if (user == null) {
            return new BjpResponse(StatusCode.UNAUTHORIZED, false, "Unauthorized user", "");
        }

        Gson gsonBuilder = new GsonBuilder().create();
        ArrayList<Offer> offers = OfferDataService.getAvailableOffers(user.id);
        return new BjpResponse(StatusCode.OK, true, "Successfully Retrieved", gsonBuilder.toJson(offers));
    }
}
