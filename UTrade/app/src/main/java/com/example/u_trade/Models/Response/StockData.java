package com.example.u_trade.Models.Response;

import java.util.ArrayList;

public class StockData{
    public String symbol;
    public ArrayList<Integer> timestamp;
    public ArrayList<Double> close;

    public StockData() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public ArrayList<Integer> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ArrayList<Integer> timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<Double> getClose() {
        return close;
    }

    public void setClose(ArrayList<Double> close) {
        this.close = close;
    }
}
