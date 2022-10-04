package com.taxi.javataxi.Classes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Driver {
    private int driverId;
    private String driverSurname;
    private String driverName;
    private String driverPatronymic;
    private String isWork;
    private String carNumber;
    private String isOwner;
    private String driverLicenseNum;
    private LocalDate dateLicense;

    public Driver(int driverId, String driverSurname, String driverName, String driverPatronymic, String isWork, String carNumber, String isOwner, String driverLicenseNum, Date dateLicense) {
        this.driverId = driverId;
        this.driverSurname = driverSurname;
        this.driverName = driverName;
        this.driverPatronymic = driverPatronymic;
        this.isWork = isWork;
        this.carNumber = carNumber;
        this.isOwner = isOwner;
        this.driverLicenseNum = driverLicenseNum;
        this.dateLicense = Instant.ofEpochMilli(dateLicense.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
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

    public String getIsWork() {
        return isWork;
    }

    public void setIsWork(String isWork) {
        this.isWork = isWork;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String isOwner() {
        return isOwner;
    }

    public void setOwner(String owner) {
        isOwner = owner;
    }

    public String getDriverLicenseNum() {
        return driverLicenseNum;
    }

    public void setDriverLicenseNum(String driverLicenseNum) {
        this.driverLicenseNum = driverLicenseNum;
    }

    public LocalDate getDateLicense() {
        return dateLicense;
    }

    public void setDateLicense(LocalDate dateLicense) {
        this.dateLicense = dateLicense;
    }
}
