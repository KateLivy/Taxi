package com.taxi.javataxi.Classes;

import java.sql.Blob;
import java.sql.SQLException;

public class Car {
    private int carId;
    private String owner;
    private String color;
    private String brand;
    private String gosNumber;
    private String registrTSNumber;
    private int numOfSeats;
    private String carCondition;
    private String yearBornCar;
    private byte[] photo;

    public Car(int carId, String owner, String registrTSNumber, String carCondition, String yearBornCar, String color, String brand, String gosNumber,  int numOfSeats, Blob photo) {
        this.carId = carId;
        this.owner = owner;
        this.color = color;
        this.brand = brand;
        this.gosNumber = gosNumber;
        this.registrTSNumber = registrTSNumber;
        this.numOfSeats = numOfSeats;
        this.carCondition = carCondition;
        this.yearBornCar = yearBornCar;
        try {
            this.photo = photo.getBytes(1, (int)photo.length());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGosNumber() {
        return gosNumber;
    }

    public void setGosNumber(String gosNumber) {
        this.gosNumber = gosNumber;
    }

    public String getRegistrTSNumber() {
        return registrTSNumber;
    }

    public void setRegistrTSNumber(String registrTSNumber) {
        this.registrTSNumber = registrTSNumber;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public String getCarCondition() {
        return carCondition;
    }

    public void setCarCondition(String carCondition) {
        this.carCondition = carCondition;
    }

    public String getYearBornCar() {
        return yearBornCar;
    }

    public void setYearBornCar(String yearBornCar) {
        this.yearBornCar = yearBornCar;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
