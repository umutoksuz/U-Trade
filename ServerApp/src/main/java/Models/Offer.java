package Models;

import Data.Entity;

import java.time.LocalDate;

public class Offer extends Entity {
    public String stockCode;
    public int amount;
    public float price;
    public boolean shortPosition;
    public int leverage;
    public LocalDate startDate;
    public LocalDate endDate;
    public int creatorId;
    public boolean isAccepted;
    public int acceptorId;
    public boolean isCreatorCheckout;
    public boolean isAcceptorCheckout;

    public Offer() {
        super();
    }

    public Offer(int creatorId, float price, String stockCode, int amount, String endDate, int leverage, boolean shortPosition){
        super();
        this.creatorId = creatorId;
        this.price = price;
        this.stockCode = stockCode;
        this.amount = amount;
        this.endDate = LocalDate.parse(endDate);
        this.leverage = leverage;
        this.shortPosition = shortPosition;
        this.acceptorId = -1;
        this.isAccepted = false;
        this.startDate = LocalDate.now();
        this.isAcceptorCheckout = false;
        this.isCreatorCheckout = false;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isShortPosition() {
        return shortPosition;
    }

    public void setShortPosition(boolean shortPosition) {
        this.shortPosition = shortPosition;
    }

    public int getLeverage() {
        return leverage;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public int getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(int acceptorId) {
        this.acceptorId = acceptorId;
    }
}
