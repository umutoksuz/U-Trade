package com.example.u_trade.Models.Request;

public class TransactionRequest {
    private int offerId;
    private double completePrice;

    public TransactionRequest() {}

    public TransactionRequest(int offerId, double completePrice) {
        this.offerId = offerId;
        this.completePrice = completePrice;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public double getCompletePrice() {
        return completePrice;
    }

    public void setCompletePrice(double completePrice) {
        this.completePrice = completePrice;
    }
}
