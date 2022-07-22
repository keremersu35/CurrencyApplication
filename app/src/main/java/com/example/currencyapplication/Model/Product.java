package com.example.currencyapplication.Model;

public class Product {

    public String nameOfProduct;
    public double priceOfProduct;
    public int numberofProduct;
    public String time;

    public Product(String nameOfProduct, double price, int numberofProduct){
        this.nameOfProduct = nameOfProduct;
        this.numberofProduct = numberofProduct;
        this.priceOfProduct = price;
    }

    public Product(String nameOfProduct, double price, int numberofProduct,String time){
        this.nameOfProduct = nameOfProduct;
        this.numberofProduct = numberofProduct;
        this.priceOfProduct = price;
        this.time = time;
    }

    public Product(){

    }
}
