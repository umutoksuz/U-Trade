
import Data.DataContext;
import Models.User;
import Services.UserDataService;
import Utils.Cryptography;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Server {
    public static void main(String[] args) throws IOException {
        //initialize database
        DataContext.initContext();
        //testDbSystem();
        ServerSocket listener = new ServerSocket(3999);

        //Always listen for clients requesting connection
        while (true){
            Socket client = listener.accept();
            WorkerThread thread = new WorkerThread(client);
            thread.run();
        }
    }

    public static void testDbSystem(){
        /*User user = new User();
        user.username = "umutoksuz";
        user.firstName = "Umut";
        user.lastName = "Oksuz";
        user.passwordHash = "";
        user.passwordSalt = "";
        user.registerDate = LocalDateTime.now();

        UserDataService.addUser(user);

        User user2 = new User();
        user2.username = "umutoksuz";
        user2.firstName = "Umut Saplardo";
        user2.lastName = "Oksuz";
        user2.passwordHash = "";
        user2.passwordSalt = "";
        user2.registerDate = LocalDateTime.now();

        UserDataService.addUser(user2);

        user.firstName = "Allah";

        boolean result = UserDataService.deleteUser(5);
        System.out.println(result);
        System.out.println(DataContext.Users.objects().get(0).firstName);*/
        String salt = Cryptography.generateSalt();
        System.out.println(Cryptography.generateJWTToken(1));
        User user = Cryptography.validateToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxNSIsImF1ZCI6InN0cmFkZV91c2VycyJ9.c759sub_RtSleelxUiI_58YbfAyQe6nQgY-WkXCd30k");
        System.out.println(user.id);
    }
}
