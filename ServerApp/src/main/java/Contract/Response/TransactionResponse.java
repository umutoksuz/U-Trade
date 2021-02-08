package Contract.Response;

public class TransactionResponse {
    private int offerId;
    private double returnMoney;

    public TransactionResponse() {}

    public TransactionResponse(int offerId, double returnMoney) {
        this.offerId = offerId;
        this.returnMoney = returnMoney;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(double returnMoney) {
        this.returnMoney = returnMoney;
    }
}
