package com.example.currencyapplication.Model;

public class Basket {

    public String nameOfProduct;
    public double priceOfProduct;
    public int numberofProduct;

    public Basket(String nameOfProduct, double price, int numberofProduct){
        this.nameOfProduct = nameOfProduct;
        this.numberofProduct = numberofProduct;
        this.priceOfProduct = price;
    }
}
