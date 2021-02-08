package Models;


import Data.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User extends Entity {
    public String username;
    public String firstName;
    public String lastName;
    public LocalDateTime registerDate;
    public String password;
    public double accountBalance;


    public User () {
        super();
    }

    public User(int id, String username, String firstName, String lastName, LocalDateTime registerDate, String password) {
        super(id);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registerDate = registerDate;
        this.password = password;
        this.accountBalance = 0;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordSalt) {
        this.password = password;
    }

}
