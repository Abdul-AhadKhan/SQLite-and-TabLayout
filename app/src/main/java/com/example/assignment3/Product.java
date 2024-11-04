package com.example.assignment3;

public class Product {
    private int id;
    private String name;
    private int price;
    private int status;

    public Product(){
        id = 0;
        name = "";
        price = 0;
        status = 1;
    }

    public Product(int id, String name, int price, int status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
