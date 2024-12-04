package com.campusmanagment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table()
public class User {

    //Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Reservatie> reservaties;


    // Constructors
    public User() {
    }
    public User(String naam, String voorNaam, LocalDateTime geboorteDatum, String email) {
        setNaam(naam);
        setVoorNaam(voorNaam);
        setGeboorteDatum(geboorteDatum);
        setEmail(email);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

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

    public List<Reservatie> getReservaties() {
        return reservaties;
    }
    public void setReservaties(List<Reservatie> reservaties) {
        this.reservaties = reservaties;
    }
}
