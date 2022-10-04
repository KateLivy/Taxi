package com.taxi.javataxi.Classes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Orders {
    private int orderId;
    private LocalDate orderDateTime;
    private String clRegion;
    private String clTown;
    private String clStreet;
    private String clHome;
    private String adrRegion;
    private String adrTown;
    private String adrStreet;
    private String adrHome;
    private String driverSurname;
    private String driverName;
    private String driverPatronymic;
    private String carNumber;
    private String clientNumber;
    private int price;
    private String completeMark;

    public Orders(int orderId, Date orderDateTime, String clRegion, String clTown, String clStreet, String clHome, String adrRegion, String adrTown, String adrStreet, String adrHome, String driverSurname, String driverName, String driverPatronymic, String carNumber, String clientNumber, int price, String completeMark) {
        this.orderId = orderId;
        this.orderDateTime = Instant.ofEpochMilli(orderDateTime.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        this.clRegion = clRegion;
        this.clTown = clTown;
        this.clStreet = clStreet;
        this.clHome = clHome;
        this.adrRegion = adrRegion;
        this.adrTown = adrTown;
        this.adrStreet = adrStreet;
        this.adrHome = adrHome;
        this.driverSurname = driverSurname;
        this.driverName = driverName;
        this.driverPatronymic = driverPatronymic;
        this.carNumber = carNumber;
        this.clientNumber = clientNumber;
        this.price = price;
        this.completeMark = completeMark;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDate orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getClRegion() {
        return clRegion;
    }

    public void setClRegion(String clRegion) {
        this.clRegion = clRegion;
    }

    public String getClTown() {
        return clTown;
    }

    public void setClTown(String clTown) {
        this.clTown = clTown;
    }

    public String getClStreet() {
        return clStreet;
    }

    public void setClStreet(String clStreet) {
        this.clStreet = clStreet;
    }

    public String getClHome() {
        return clHome;
    }

    public void setClHome(String clHome) {
        this.clHome = clHome;
    }

    public String getAdrRegion() {
        return adrRegion;
    }

    public void setAdrRegion(String adrRegion) {
        this.adrRegion = adrRegion;
    }

    public String getAdrTown() {
        return adrTown;
    }

    public void setAdrTown(String adrTown) {
        this.adrTown = adrTown;
    }

    public String getAdrStreet() {
        return adrStreet;
    }

    public void setAdrStreet(String adrStreet) {
        this.adrStreet = adrStreet;
    }

    public String getAdrHome() {
        return adrHome;
    }

    public void setAdrHome(String adrHome) {
        this.adrHome = adrHome;
    }

    public String getDriverSurname() {
        return driverSurname;
    }

    public void setDriverSurname(String driverSurname) {
        this.driverSurname = driverSurname;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPatronymic() {
        return driverPatronymic;
    }

    public void setDriverPatronymic(String driverPatronymic) {
        this.driverPatronymic = driverPatronymic;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCompleteMark() {
        return completeMark;
    }

    public void setCompleteMark(String completeMark) {
        this.completeMark = completeMark;
    }
}
