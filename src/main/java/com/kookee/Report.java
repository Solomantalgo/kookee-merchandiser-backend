package com.kookee.merchandiser_backend;
import com.kookee.merchandiser_backend.Item;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String merchandiser;
    private String outlet;
    private LocalDate date;

    @ElementCollection
    @CollectionTable(name = "report_items", joinColumns = @JoinColumn(name = "report_id"))
    private List<Item> items;

    // No-args constructor
    public Report() {}

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getMerchandiser() {
        return merchandiser;
    }

    public void setMerchandiser(String merchandiser) {
        this.merchandiser = merchandiser;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    // Optionally: toString(), equals(), hashCode()
}