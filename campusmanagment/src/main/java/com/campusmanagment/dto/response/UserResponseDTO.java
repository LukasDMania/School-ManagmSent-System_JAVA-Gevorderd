package com.campusmanagment.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDTO {

    // Fields
    private String naam;
    private String voornaam;
    private LocalDateTime geboorteDatum;
    private String email;

    // Relations
    private List<ReservatieResponseDTO> reservatieResponseDTOs;

    // Constructors
    public UserResponseDTO() {}
    public UserResponseDTO(String naam, String voornaam, LocalDateTime geboorteDatum, String email, List<ReservatieResponseDTO> reservaties) {
        this.naam = naam;
        this.voornaam = voornaam;
        this.geboorteDatum = geboorteDatum;
        this.email = email;
        this.reservatieResponseDTOs = reservaties;
    }
    //  No Reservaties
    public UserResponseDTO(String naam, String voornaam, LocalDateTime geboorteDatum, String email) {
        this.naam = naam;
        this.voornaam = voornaam;
        this.geboorteDatum = geboorteDatum;
        this.email = email;
    }

    // Getters and Setters
    public String getNaam() {
        return naam;
    }
    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoornaam() {
        return voornaam;
    }
    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public LocalDateTime getGeboorteDatum() {
        return geboorteDatum;
    }
    public void setGeboorteDatum(LocalDateTime geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<ReservatieResponseDTO> getReservatieResponseDTOs() {
        return reservatieResponseDTOs;
    }
    public void setReservatieResponseDTOs(List<ReservatieResponseDTO> reservatieResponseDTOs) {
        this.reservatieResponseDTOs = reservatieResponseDTOs;
    }

    // Override Methods
    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "naam='" + naam + '\'' +
                ", voornaam='" + voornaam + '\'' +
                ", geboorteDatum=" + geboorteDatum +
                ", email='" + email + '\'' +
                '}';
    }
}
