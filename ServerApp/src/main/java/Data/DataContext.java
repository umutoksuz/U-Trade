package Data;

import Models.Offer;
import Models.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DataContext {
    public static DbSet<User> Users;
    public static DbSet<Offer> Offers;

    public static void initContext() {
        Users = new DbSet<User>("users.txt", User.class);
        Offers = new DbSet<Offer>("offers.txt", Offer.class);
    }
}
