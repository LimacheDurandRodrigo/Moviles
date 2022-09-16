package com.example.proyectoagriaplicacion.model;

public class ListProduct {
    public String color;
    public String name;
    public String description;
    public String status;

    public ListProduct(String color, String name, String description, String status) {
        this.color = color;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
