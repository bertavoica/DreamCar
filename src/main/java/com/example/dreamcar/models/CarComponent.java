package com.example.dreamcar.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "CarComponent")
public class CarComponent {
    @Id
    private String id;

    private String name;
    private String color;
    private String description;
    private float price;
    private int timeout;
    private LocalDateTime addedDate;
    private int quantity;

    private AuctionStatus status;

    public CarComponent() {
    }
    public CarComponent(String name, String description, int timeoutDays, float price, int quantity) {
        this.name = name;
        this.description = description;
        this.timeout = timeoutDays;
        this.price = price;
        this.quantity = quantity;
        this.addedDate = LocalDateTime.now();
        this.status = AuctionStatus.ON_GOING;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
