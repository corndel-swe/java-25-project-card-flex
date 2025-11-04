package org.project.cardflex.Model;
// FY
public class Cards {
    private int id;
    private int userId;
    private int accountNumber;
    private float creditLimit;
    private float APR;
    private String startDate;
    private String refreshDate;
    private String cardName;
    private int balance;

    // --------------- Constructors for cards -------------- //
    public Cards(int id, int userId, int accountNumber, float creditLimit, float APR, String startDate, String refreshDate, String cardName) {
        this.id = id;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.creditLimit = creditLimit;
        this.APR = APR;
        this.startDate = startDate;
        this.refreshDate = refreshDate;
        this.cardName = cardName;
    }

    public Cards(int accountNumber, float creditLimit, float APR, String cardName, String startDate, int balance) {
        this.accountNumber = accountNumber;
        this.creditLimit = creditLimit;
        this.APR = APR;
        this.cardName = cardName;
        this.startDate = startDate;
        this.balance = balance;
    }

    public Cards(int userId, int accountNumber, float creditLimit, float APR, String cardName, String startDate, String refreshDate) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.creditLimit = creditLimit;
        this.APR = APR;
        this.cardName = cardName;
        this.startDate = startDate;
        this.refreshDate = refreshDate;
    }


    // ------------- Getters And Setters ------------------- //
    public Cards(int userId) {
        this.userId = userId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public float getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(float creditLimit) {
        this.creditLimit = creditLimit;
    }

    public float getAPR() {
        return APR;
    }

    public void setAPR(float APR) {
        this.APR = APR;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(String refreshDate) {
        this.refreshDate = refreshDate;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}


