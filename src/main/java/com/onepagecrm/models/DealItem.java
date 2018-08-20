package com.onepagecrm.models;

import java.io.Serializable;

/**
 * Created by Anton S. on 04/08/2018
 */
public class DealItem implements Serializable {

    private String id;
    private String name;
    private String description;
    private Double cost;
    private Double price;
    private Integer amount;
    private Integer quantity;
    private String dealId;
    private String itemGroupId;
    private String predefinedItemId;
    private String createdAt; // TODO: Instant??
    private String modifiedAt; // TODO: Instant??


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

    public Double getCost() {
        return cost;
    }

    public DealItem setCost(Double cost) {
        this.cost = cost;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public DealItem setPrice(Double price) {
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

    public String getModifiedAt() {
        return modifiedAt;
    }

    public DealItem setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public DealItem setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}