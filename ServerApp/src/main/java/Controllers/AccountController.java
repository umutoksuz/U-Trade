package Controllers;

import Contract.Request.AddBalanceRequest;
import Contract.Request.LoginRequest;
import Contract.Request.RegisterRequest;
import Contract.Response.LoginResponse;
import Contract.Response.RegisterResponse;
import Models.User;
import Protocol.*;
import Services.UserDataService;
import Utils.Cryptography;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.time.LocalDateTime;


public class AccountController {

    /*
    * Login functionality implemented here
    * */
    public static BjpResponse Login(LoginRequest request){
        Gson gsonBuilder = new GsonBuilder().create();
        if(request == null)
            return new BjpResponse(StatusCode.SERVER_ERROR, false, "Invalid json format.", "");

        if(!request.isValid()){
            return new BjpResponse(StatusCode.BAD_REQUEST, false, "Some required fields are null", "");
        }

        User user = UserDataService.getUserByUsername(request.username);
        if(user == null || !user.password.equals(request.password)){
            return new BjpResponse(StatusCode.OK, false, "Incorrect username or password", "");
        }

        LoginResponse responseData =
                new LoginResponse(user.id, user.username, Cryptography.generateJWTToken(user.id), user.firstName, user.lastName, user.accountBalance);
        return new BjpResponse(StatusCode.OK, true, "Logged in successfully.", gsonBuilder.toJson(responseData));

    }

    public static BjpResponse SignUp(RegisterRequest request) {
        Gson gsonBuilder = new GsonBuilder().create();
        if(request == null)
            return new BjpResponse(StatusCode.SERVER_ERROR, false, "Invalid json format.", "");
        if(!request.isValid()){
            return new BjpResponse(StatusCode.BAD_REQUEST, false, "Some required fields are null", "");
        }

        if (UserDataService.getUserByUsername(request.username) != null) {
            return new BjpResponse(StatusCode.BAD_REQUEST, false, "Username already exists. Try another one.", "");
        }

        User user = new User();
        user.accountBalance = 0;
        user.registerDate = LocalDateTime.now();
        user.username = request.username;
        user.firstName = request.firstName;
        user.lastName = request.lastName;
        user.password = request.password;
        int id = UserDataService.addUser(user);

        System.out.println(id + " created");

        String token = Cryptography.generateJWTToken(id);

        RegisterResponse data = new RegisterResponse(id, user.firstName, user.lastName, user.username, token, user.accountBalance, user.registerDate);
        return new BjpResponse(StatusCode.OK, true, "Logged in successfully.", gsonBuilder.toJson(data));
    }

    public static BjpResponse addBalance(User user, AddBalanceRequest request) {
        System.out.println(user.id+ " " + request.getBalanceToAdd());
        if (user == null) {
            return new BjpResponse(StatusCode.UNAUTHORIZED, false, "Unauthorized user", "");
        }

        if (request.getBalanceToAdd() < 0) {
            return new BjpResponse(StatusCode.BAD_REQUEST, false, "Negative balance", "");
        }

        user.accountBalance += request.getBalanceToAdd();
        UserDataService.updateUser(user);

        return new BjpResponse(StatusCode.OK, true, "Balance successfully added.", "");
    }
}
