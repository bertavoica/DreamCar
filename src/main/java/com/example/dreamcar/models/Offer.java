package com.example.dreamcar.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Offer")
public class Offer {
    @Id
    private String id;
    private String username;
    private float price;
    private boolean applicationStatus;
    private String carComponentName;
    private LocalDateTime dateAdded;

    public Offer() {
    }

    public Offer(String name, float price, String username) {
        this.carComponentName = name;
        this.price = price;
        this.username = username;
        this.dateAdded = LocalDateTime.now();
    }

    public boolean isApplicationStatus() {
        return applicationStatus;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(boolean applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getCarComponentName() {
        return carComponentName;
    }

    public void setCarComponentName(String carComponentName) {
        this.carComponentName = carComponentName;
    }
}
