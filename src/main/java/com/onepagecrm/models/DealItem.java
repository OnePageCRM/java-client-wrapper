package com.onepagecrm.models;

import java.io.Serializable;

/**
 * Created by Anton S. on 04/08/2018
 */
public class DealItem implements Serializable {

    private String id;
    private String name;
    private String description;
    private int cost;
    private int price;
    private int amount;
    private String quantity;
    private String dealId;
    private String itemGroupId;
    private String predefinedItemId;
    private String createdAt;
    private String updatedAt;


    public String getId() {
        return id;
    }

    public DealItem setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DealItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DealItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getCost() {
        return cost;
    }

    public DealItem setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public DealItem setPrice(int price) {
        this.price = price;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public DealItem setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getDealId() {
        return dealId;
    }

    public DealItem setDealId(String dealId) {
        this.dealId = dealId;
        return this;
    }

    public String getItemGroupId() {
        return itemGroupId;
    }

    public DealItem setItemGroupId(String itemGroupId) {
        this.itemGroupId = itemGroupId;
        return this;
    }

    public String getPredefinedItemId() {
        return predefinedItemId;
    }

    public DealItem setPredefinedItemId(String predefinedItemId) {
        this.predefinedItemId = predefinedItemId;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public DealItem setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public DealItem setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }

    public DealItem setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }
}