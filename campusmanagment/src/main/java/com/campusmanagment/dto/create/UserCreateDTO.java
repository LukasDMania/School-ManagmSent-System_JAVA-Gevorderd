package com.campusmanagment.dto.create;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class UserCreateDTO {

    // Fields
    @NotBlank(message = "Naam is verplicht")
    @Size(min = 1, max = 50, message = "Naam is verplicht en moet tussen de 1 en 50 karakters zijn")
    private String naam;

    @NotBlank(message = "Voornaam is verplicht")
    @Size(min = 1, max = 50, message = "Voornaam is verplicht en moet tussen de 1 en 50 karakters zijn")
    private String voorNaam;

    @NotNull(message = "Geboortedatum is verplicht")
    @Past(message = "Geboortedatum moet in het verleden zijn")
    private LocalDateTime geboorteDatum;

    @NotBlank(message = "Email is verplicht")
    @Email(message = "Email moet een geldig email adres zijn")
    @Size(min = 1, max = 255, message = "Email is verplicht en moet tussen de 1 en 255 karakters zijn")
    private String email;

    // Constructors
    public UserCreateDTO() {}
    public UserCreateDTO(String naam, String voorNaam, LocalDateTime geboorteDatum, String email) {
        this.naam = naam;
        this.voorNaam = voorNaam;
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

    public String getVoorNaam() {
        return voorNaam;
    }
    public void setVoorNaam(String voorNaam) {
        this.voorNaam = voorNaam;
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

    //Override Methods
    @Override
    public String toString() {
        return "UserCreateDTO{" +
                "naam='" + naam + '\'' +
                ", voorNaam='" + voorNaam + '\'' +
                ", geboorteDatum=" + geboorteDatum +
                ", email='" + email + '\'' +
                '}';
    }
}
