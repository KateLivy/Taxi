package com.taxi.javataxi.Classes;

public class Client {
    private int clientId;
    private String clientNumber;

    public Client(int clientId, String clientNumber) {
        this.clientId = clientId;
        this.clientNumber = clientNumber;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }
}
