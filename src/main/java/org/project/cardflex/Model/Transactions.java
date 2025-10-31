package org.project.cardflex.Model;
//EB
public class Transactions {
    private int id;
    private int sendersAccNum; //sendersAccountNumber
    private int sendersCardId;
    private String sendersUsername;
    private int recipientAccNum; //recipientAccountNumber
    private String recipientUsername;
    private float amount;
    private String description;
    private String transactionDate;

    //Constructor for all variables
    public Transactions(int id, int sendersAccNum, int sendersCardId, String sendersUsername, int recipientAccNum, String recipientUsername, float amount, String description, String transactionDate) {
        this.id = id;
        this.sendersAccNum = sendersAccNum;
        this.sendersCardId = sendersCardId;
        this.sendersUsername = sendersUsername;
        this.recipientAccNum = recipientAccNum;
        this.recipientUsername = recipientUsername;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    //Constructor excluding 'id' variable - generally will be most common used transaction constructor
    public Transactions(int sendersAccNum, int sendersCardId, String sendersUsername, int recipientAccNum, String recipientUsername, float amount, String description, String transactionDate) {
        this.sendersAccNum = sendersAccNum;
        this.sendersCardId = sendersCardId;
        this.sendersUsername = sendersUsername;
        this.recipientAccNum = recipientAccNum;
        this.recipientUsername = recipientUsername;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }


    //Transaction getters
    public int getId() {
        return id;
    }

    public int getSendersAccNum() {
        return sendersAccNum;
    }

    public int getSendersCardId() {
        return sendersCardId;
    }

    public String getSendersUsername() {
        return sendersUsername;
    }

    public int getRecipientAccNum() {
        return recipientAccNum;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public float getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    //Transaction setters
    public void setId(int id) {
        this.id = id;
    }

    public void setSendersAccNum(int sendersAccNum) {
        this.sendersAccNum = sendersAccNum;
    }

    public void setSendersCardId(int sendersCardId) {
        this.sendersCardId = sendersCardId;
    }

    public void setSendersUsername(String sendersUsername) {
        this.sendersUsername = sendersUsername;
    }

    public void setRecipientAccNum(int recipientAccNum) {
        this.recipientAccNum = recipientAccNum;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
