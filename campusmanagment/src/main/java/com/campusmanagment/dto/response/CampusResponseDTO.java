package com.campusmanagment.dto.response;

import com.campusmanagment.model.Adres;
import com.campusmanagment.model.Campus;

import java.time.LocalDateTime;
import java.util.List;

public class CampusResponseDTO {

    // Fields
    private String campusNaam;
    private Adres adres;
    private int parkeerplaatsen;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Relations
    private List<LokaalResponseDTO> lokalenResponseDTOs;

    // Constructors
    public CampusResponseDTO() {}
    public CampusResponseDTO(Campus campus, List<LokaalResponseDTO> lokalen) {
        this.campusNaam = campus.getCampusNaam();
        this.adres = campus.getAdres();
        this.parkeerplaatsen = campus.getParkeerplaatsen();
        this.createdAt = campus.getCreatedAt();
        this.updatedAt = campus.getUpdatedAt();
        this.lokalenResponseDTOs = lokalen;
    }
    //  No Lokalen
    public CampusResponseDTO(Campus campus){
        this.campusNaam = campus.getCampusNaam();
        this.adres = campus.getAdres();
        this.parkeerplaatsen = campus.getParkeerplaatsen();
        this.createdAt = campus.getCreatedAt();
        this.updatedAt = campus.getUpdatedAt();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<LokaalResponseDTO> getLokalenResponseDTOs() {
        return lokalenResponseDTOs;
    }
    public void setLokalen(List<LokaalResponseDTO> lokalen) {
        this.lokalenResponseDTOs = lokalen;
    }

    // Override Methods
    @Override
    public String toString() {
        return "CampusResponseDTO{" +
                "campusNaam='" + campusNaam + '\'' +
                ", adres=" + adres +
                ", parkeerplaatsen=" + parkeerplaatsen +
                ", lokalen=" + lokalenResponseDTOs +
                '}';
    }
}
