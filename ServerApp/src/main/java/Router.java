import Contract.Request.*;
import Controllers.AccountController;
import Controllers.OfferController;
import Controllers.TransactionController;
import Protocol.BjpRequest;
import Protocol.BjpResponse;
import Utils.Cryptography;
import Utils.RequestParser;


public class Router {
    public static BjpResponse routeRequest(BjpRequest request){
        if(request.getRoute().equalsIgnoreCase("signin")){
            return AccountController.Login(RequestParser.parse(request, LoginRequest.class));
        }
        if(request.getRoute().equalsIgnoreCase("signup")){
            return AccountController.SignUp(RequestParser.parse(request, RegisterRequest.class));
        }
        if(request.getRoute().equalsIgnoreCase("createoffer")){
            return OfferController.createOffer(Cryptography.validateToken(request.getToken()), RequestParser.parse(request, PostOfferRequest.class));
        }
        if(request.getRoute().equalsIgnoreCase("alloffers")){
            return OfferController.getAvailableOffers(Cryptography.validateToken(request.getToken()));
        }
        if(request.getRoute().equalsIgnoreCase("myoffers")){
            return OfferController.getMyOffers(Cryptography.validateToken(request.getToken()));
        }
        if(request.getRoute().equalsIgnoreCase("canceloffer")){
            return OfferController.cancelOffer(Cryptography.validateToken(request.getToken()), RequestParser.parse(request, SingleIdRequest.class));
        }
        if(request.getRoute().equalsIgnoreCase("acceptoffer")){
            return OfferController.acceptOffer(Cryptography.validateToken(request.getToken()), RequestParser.parse(request, SingleIdRequest.class));
        }
        if(request.getRoute().equalsIgnoreCase("addbalance")) {
            return AccountController.addBalance(Cryptography.validateToken(request.getToken()), RequestParser.parse(request, AddBalanceRequest.class));
        }
        if(request.getRoute().equalsIgnoreCase("transaction")) {
            return TransactionController.ClaimBalance(Cryptography.validateToken(request.getToken()), RequestParser.parse(request, TransactionRequest.class));
        }

        return null;
    }
}
