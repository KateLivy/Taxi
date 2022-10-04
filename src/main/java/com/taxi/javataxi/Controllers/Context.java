package com.taxi.javataxi.Controllers;


/** Класс для связки добавления заказов и адреса*/
public class Context {
    public final static Context instance = new Context();
    public static Context getInstance() {
        return instance;
    }

    public AddOrderController addOrderController;
    public void setAddOrderController(AddOrderController addOrderController) {
        this.addOrderController=addOrderController;
    }

    public AddOrderController getAddOrderController() {
        return addOrderController;
    }

    public AddAddress addAddress;
    public void setAddAddress(AddAddress addAddress) {
        this.addAddress=addAddress;
    }

    public AddAddress getAddAddress() {
        return addAddress;
    }

}
