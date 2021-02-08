package Contract.Request;

import java.time.LocalDate;

public class PostOfferRequest {
    public String stockCode;
    public int amount;
    public float price;
    public boolean shortPosition;
    public int leverage;
    public String endDate;

    public PostOfferRequest() {
    }

    public PostOfferRequest(String stockCode, int amount, float price, boolean shortPosition, int leverage, String endDate) {
        this.stockCode = stockCode;
        this.amount = amount;
        this.price = price;
        this.shortPosition = shortPosition;
        this.leverage = leverage;
        this.endDate = endDate;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
