package org.project.cardflex;

public class User {
    private int id;
    private String username;
    private float totalBalance;

    public User(int id, String username, float totalBalance) {
        this.id = id;
        this.username = username;
        this.totalBalance = totalBalance;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setTotalBalance(float totalBalance) {
        this.totalBalance = totalBalance;
    }

    public float getTotalBalance() {
        return totalBalance;
    }
}



