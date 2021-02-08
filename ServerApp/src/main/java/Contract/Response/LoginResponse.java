package Contract.Response;

public class LoginResponse {
    private int id;
    private String username;
    private String token;
    private String firstName;
    private String lastName;
    private double accountBalance;


    public LoginResponse() {
        this.id = 0;
        this.username = "";
        this.token = "";
        this.firstName = "";
        this.lastName = "";
        this.accountBalance = 0;
    }

    public LoginResponse(int id, String username, String token, String firstName, String lastName, double accountBalance) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountBalance = accountBalance;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
