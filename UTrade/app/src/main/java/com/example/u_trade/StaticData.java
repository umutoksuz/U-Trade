package com.example.u_trade;

import com.example.u_trade.Models.Response.LoginResponse;
import com.example.u_trade.Models.Response.RegisterResponse;
import com.example.u_trade.Models.Stock;

import java.util.ArrayList;

public class StaticData {
    public static boolean loggedIn = false;
    public static int userId = 0;
    public static String firstName = "";
    public static String lastName = "";
    public static String username = "";
    public static String token = "";
    public static double accountBalance = 0;

    public static String stockApiBaseUrl = "https://yahoo-finance-low-latency.p.rapidapi.com";
    public static ArrayList<Stock> availableStocks;

    public static void initStockData() {
        availableStocks = new ArrayList<>();
    }

    public static void mapFromLoginResponse(LoginResponse response){
        loggedIn = true;
        userId = response.getId();
        firstName = response.getFirstName();
        lastName = response.getLastName();
        username = response.getUsername();
        token = response.getToken();
        accountBalance = response.getAccountBalance();
    }

    public static void mapFromRegisterResponse(RegisterResponse response) {
        loggedIn = true;
        userId = response.getId();
        firstName = response.getFirstName();
        lastName = response.getLastName();
        username = response.getUsername();
        token = response.getToken();
        accountBalance = response.getAccountBalance();
    }
}
