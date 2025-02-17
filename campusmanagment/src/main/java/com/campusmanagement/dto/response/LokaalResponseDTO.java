package com.campusmanagement.dto.response;

import com.campusmanagement.util.LokaalType;

import java.util.ArrayList;
import java.util.List;

public class LokaalResponseDTO {

    //Fields
    private String lokaalNaam;
    private LokaalType lokaalType;
    private int capaciteit;
    private int verdieping;

    // Relations
    private CampusResponseDTO campusResponseDTO;
    private List<ReservatieResponseDTO> reservatieResponseDTOs = new ArrayList<>();

    // Constructors
    public LokaalResponseDTO() {}
    public LokaalResponseDTO(String lokaalNaam, LokaalType lokaalType, int capaciteit, int verdieping, CampusResponseDTO campusResponseDTO, List<ReservatieResponseDTO> reservaties) {
        this.lokaalNaam = lokaalNaam;
        this.lokaalType = lokaalType;
        this.capaciteit = capaciteit;
        this.verdieping = verdieping;
        this.campusResponseDTO = campusResponseDTO;
        this.reservatieResponseDTOs = reservaties;
    }
    //  No CampusResponseDTO
    public LokaalResponseDTO(String lokaalNaam, LokaalType lokaalType, int capaciteit, int verdieping, List<ReservatieResponseDTO> reservaties) {
        this.lokaalNaam = lokaalNaam;
        this.lokaalType = lokaalType;
        this.capaciteit = capaciteit;
        this.verdieping = verdieping;
        this.reservatieResponseDTOs = reservaties;
    }
    //  No Reservaties
    public LokaalResponseDTO(String lokaalNaam, LokaalType lokaalType, int capaciteit, int verdieping, CampusResponseDTO campusResponseDTO) {
        this.lokaalNaam = lokaalNaam;
        this.lokaalType = lokaalType;
        this.capaciteit = capaciteit;
        this.verdieping = verdieping;
        this.campusResponseDTO = campusResponseDTO;
    }
    //  No CampusResponseDTO and Reservaties
    public LokaalResponseDTO(String lokaalNaam, LokaalType lokaalType, int capaciteit, int verdieping) {
        this.lokaalNaam = lokaalNaam;
        this.lokaalType = lokaalType;
        this.capaciteit = capaciteit;
        this.verdieping = verdieping;
    }

    // Getters and Setters
    public String getLokaalNaam() {
        return lokaalNaam;
    }
    public void setLokaalNaam(String lokaalNaam) {
        this.lokaalNaam = lokaalNaam;
    }

    public LokaalType getLokaalType() {
        return lokaalType;
    }
    public void setLokaalType(LokaalType lokaalType) {
        this.lokaalType = lokaalType;
    }

    public int getCapaciteit() {
        return capaciteit;
    }
    public void setCapaciteit(int capaciteit) {
        this.capaciteit = capaciteit;
    }

    public int getVerdieping() {
        return verdieping;
    }
    public void setVerdieping(int verdieping) {
        this.verdieping = verdieping;
    }

    public CampusResponseDTO getCampusResponseDTO() {
        return campusResponseDTO;
    }
    public void setCampusResponseDTO(CampusResponseDTO campusResponseDTO) {
        this.campusResponseDTO = campusResponseDTO;
    }

    public List<ReservatieResponseDTO> getReservatieResponseDTOs() {
        return reservatieResponseDTOs;
    }
    public void setReservatiesResponseDTOs1(List<ReservatieResponseDTO> reservaties) {
        this.reservatieResponseDTOs = reservaties;
    }

    // Override Methods
    @Override
    public String toString() {
        return "LokaalResponseDTO{" +
                "lokaalNaam='" + lokaalNaam + '\'' +
                ", lokaalType=" + lokaalType +
                ", capaciteit=" + capaciteit +
                ", verdieping=" + verdieping +
                ", campusResponseDTO=" + campusResponseDTO +
                ", reservaties=" + reservatieResponseDTOs +
                '}';
    }
}
