package Services;

import Data.DataContext;
import Data.DbSet;
import Models.User;
import java.util.ArrayList;

public class UserDataService {

    public static User getUserById(int id){
        return DataContext.Users.getObjectById(id);
    }

    public static User getUserByUsername(String username) {
        User result = null;
        ArrayList<User> users = DataContext.Users.objects();
        for(User user: users) {
            if (user.username.equals(username)){
                result = user;
                break;
            }
        }
        return result;
    }

    public static int addUser(User user) {
        int id = DataContext.Users.add(user);
        DataContext.Users.saveChanges();
        return id;
    }

    public static boolean updateUser(User user) {
        boolean result = DataContext.Users.updateObject(user);
        DataContext.Users.saveChanges();
        return result;
    }

    public static boolean deleteUser(int id) {
        User user = DataContext.Users.getObjectById(id);
        if (user == null) {
            return false;
        }
        boolean result = DataContext.Users.delete(user);
        DataContext.Users.saveChanges();
        return  result;
    }
}
