package com.campusmanagement.dto.create;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UserCreateDTO {

    // Fields
    @NotBlank(message = "Naam is verplicht")
    @Size(min = 1, max = 50, message = "Naam is verplicht en moet tussen de 1 en 50 karakters zijn")
    private String naam;

    @NotBlank(message = "Voornaam is verplicht")
    @Size(min = 1, max = 50, message = "Voornaam is verplicht en moet tussen de 1 en 50 karakters zijn")
    private String voornaam;

    @NotNull(message = "Geboortedatum is verplicht")
    @Past(message = "Geboortedatum moet in het verleden zijn")
    private LocalDate geboorteDatum;

    @NotBlank(message = "Email is verplicht")
    @Email(message = "Email moet een geldig email adres zijn")
    @Size(min = 1, max = 255, message = "Email is verplicht en moet tussen de 1 en 255 karakters zijn")
    private String email;

    // Constructors
    public UserCreateDTO() {}
    public UserCreateDTO(String naam, String voornaam, LocalDate geboorteDatum, String email) {
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
    public void setVoornaam(String voorNaam) {
        this.voornaam = voorNaam;
    }

    public LocalDate getGeboorteDatum() {
        return geboorteDatum;
    }
    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    //Override Methods
    @Override
    public String toString() {
        return "UserCreateDTO{" +
                "naam='" + naam + '\'' +
                ", voorNaam='" + voornaam + '\'' +
                ", geboorteDatum=" + geboorteDatum +
                ", email='" + email + '\'' +
                '}';
    }
}
