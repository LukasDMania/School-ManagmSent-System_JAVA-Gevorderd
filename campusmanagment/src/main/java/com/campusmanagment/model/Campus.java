package com.campusmanagment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Campus {

    @Id
    private String name;
    private Adres adres;
    private int parkeerplaatsen;

    // Relations
    @OneToMany(mappedBy = "campus")
    private List<Lokaal> lokalen;



    public int getAantalLokalen() {
        return lokalen == null ? 0 : lokalen.size();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public int getParkeerplaatsen() {
        return parkeerplaatsen;
    }

    public void setParkeerplaatsen(int parkeerplaatsen) {
        this.parkeerplaatsen = parkeerplaatsen;
    }

    public List<Lokaal> getLokalen() {
        return lokalen;
    }

    public void setLokalen(List<Lokaal> lokalen) {
        this.lokalen = lokalen;
    }
}
