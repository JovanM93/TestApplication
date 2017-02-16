package com.example.jovan.testapplication;



public class Blogs {
    private int id;
    private String  titlee;
    private String  imageUrle;
    private String  description;

    public Blogs(int id, String titlee, String imageUrle, String description) {
        this.id = id;
        this.titlee = titlee;
        this.imageUrle = imageUrle;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitlee() {
        return titlee;
    }

    public void setTitlee(String titlee) {
        this.titlee = titlee;
    }

    public String getImageUrle() {
        return imageUrle;
    }

    public void setImageUrle(String imageUrle) {
        this.imageUrle = imageUrle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
