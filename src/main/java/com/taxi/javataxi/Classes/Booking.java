package com.taxi.javataxi.Classes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Booking {
    private int bookId;
    private LocalDate bookDateTime;
    private String clRegion;
    private String clTown;
    private String clStreet;
    private String clHome;
    private String adrRegion;
    private String adrTown;
    private String adrStreet;
    private String adrHome;
    private String clientNumber;
    private int price;
    private int numOfSeats;

    public Booking(int bookId, Date bookDateTime, String clRegion, String clTown, String clStreet, String clHome, String adrRegion, String adrTown, String adrStreet, String adrHome, String clientNumber, int price, int numOfSeats) {
        this.bookId = bookId;
        this.bookDateTime = Instant.ofEpochMilli(bookDateTime.getTime())
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
        this.clientNumber = clientNumber;
        this.price = price;
        this.numOfSeats = numOfSeats;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public LocalDate getBookDateTime() {
        return bookDateTime;
    }

    public void setBookDateTime(LocalDate bookDateTime) {
        this.bookDateTime = bookDateTime;
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

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }
}
