package com.taxi.javataxi.Controllers;

/** Класс для связки бронирований с адресом. Не используется.*/
public class Context1 {
    public final static Context1 instance = new Context1();
    public static Context1 getInstance() {
        return instance;
    }

    public AddBookingController addBookingController;
    public void setAddBookingController(AddBookingController addBookingController) {
        this.addBookingController=addBookingController;
    }

    public AddBookingController getAddBookingController() {
        return addBookingController;
    }

    public AddAddress addAddress;
    public void setAddAddress(AddAddress addAddress) {
        this.addAddress=addAddress;
    }

    public AddAddress getAddAddress() {
        return addAddress;
    }
}
