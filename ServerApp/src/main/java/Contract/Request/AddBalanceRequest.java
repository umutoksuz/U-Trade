package Contract.Request;

public class AddBalanceRequest {
    private double balanceToAdd;

    public AddBalanceRequest() {
    }

    public AddBalanceRequest(double balanceToAdd) {
        this.balanceToAdd = balanceToAdd;
    }

    public double getBalanceToAdd() {
        return balanceToAdd;
    }

    public void setBalanceToAdd(double balanceToAdd) {
        this.balanceToAdd = balanceToAdd;
    }
}
