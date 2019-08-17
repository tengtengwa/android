package com.example.recyclerviewtest;

public class Fruit {
    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    private String name;
    private int imageId;

    public Fruit(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }


}
