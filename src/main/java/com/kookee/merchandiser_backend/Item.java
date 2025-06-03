// filepath: c:\Users\HP\OneDrive\Desktop\kookee-App\merchandiser_backend\src\main\java\com\kookee\merchandiser_backend\Item.java
package com.kookee.merchandiser_backend;

import jakarta.persistence.Embeddable;

@Embeddable
public class Item {
    private String name;
    private Integer qty;

    public Item() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getQty() {
        return qty;
    }
    public void setQty(Integer qty) {
        this.qty = qty;
    }
}