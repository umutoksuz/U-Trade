package Services;

import Data.DataContext;
import Models.Offer;

import java.time.LocalDate;
import java.util.ArrayList;

public class OfferDataService {

    public static Offer getOfferById(int id) {
        return DataContext.Offers.getObjectById(id);
    }

    public static ArrayList<Offer> getAvailableOffers(int userId) {
        ArrayList<Offer> allOffers = DataContext.Offers.objects();
        ArrayList<Offer> result = new ArrayList<Offer>();
        for(Offer offer : allOffers) {
            if(offer.endDate.isAfter(LocalDate.now()) && !offer.isAccepted && offer.creatorId != userId){
                result.add(offer);
            }
        }
        return result;
    }

    public static int addOffer(Offer offer) {
        int id = DataContext.Offers.add(offer);
        DataContext.Offers.saveChanges();
        return id;
    }

    public static boolean deleteOffer(Offer offer) {
        boolean isDeleted = DataContext.Offers.delete(offer);
        DataContext.Offers.saveChanges();
        return isDeleted;
    }

    public static boolean updateOffer(Offer offer) {
        boolean isUpdated = DataContext.Offers.updateObject(offer);
        DataContext.Offers.saveChanges();
        return isUpdated;
    }

    public static ArrayList<Offer> getOffersByUserId(int userId) {
        ArrayList<Offer> result = new ArrayList<>();
        ArrayList<Offer> allOffers = DataContext.Offers.objects();
        for(Offer offer : allOffers){
            if(offer.creatorId == userId){
                result.add(offer);
            }
            if(offer.acceptorId == userId) {
                result.add(offer);
            }
        }
        return result;
    }
}
