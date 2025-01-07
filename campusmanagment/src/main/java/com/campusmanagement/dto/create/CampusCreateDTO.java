package com.campusmanagement.dto.create;

import com.campusmanagement.model.Adres;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class CampusCreateDTO {

    // Fields
    @NotBlank(message = "Campus naam mag niet leeg zijn")
    @Size(min = 3, max = 255, message = "Campus naam moet tussen de 3 en 255 characters zijn")
    private String campusNaam;

    @Valid
    @NotNull(message = "Adres moet worden opgegeven")
    private Adres adres;

    @Positive(message = "Parkeerplaatsen kan geen negatief nummer zijn")
    @Max(value = 10000, message = "Parkeerplaatsen kan niet groter zijn dan 10000")
    private int parkeerplaatsen;

    // Constructors, getters, and setters
    public CampusCreateDTO() {}

    public CampusCreateDTO(String campusNaam, Adres adres, int parkeerplaatsen) {
        this.campusNaam = campusNaam;
        this.adres = adres;
        this.parkeerplaatsen = parkeerplaatsen;
    }

    // Getters and Setters
    public String getCampusNaam() {
        return campusNaam;
    }
    public void setCampusNaam(String campusNaam) {
        this.campusNaam = campusNaam;
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

    // Override Methods
    @Override
    public String toString(){
        return "CampusCreateDTO{" +
                "campusNaam='" + campusNaam + '\'' +
                ", parkeerplaatsen=" + parkeerplaatsen +
                ", straat=" + adres.getStraat() +
                ", stad=" + adres.getStad() +
                ", postcode=" + adres.getPostcode() +
                '}';
    }
}
