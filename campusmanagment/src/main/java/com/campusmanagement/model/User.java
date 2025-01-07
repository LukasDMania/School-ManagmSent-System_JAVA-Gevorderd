package com.campusmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
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
    private String voornaam;

    @NotNull(message = "Geboortedatum is verplicht")
    @Past(message = "Geboortedatum moet in het verleden zijn")
    private LocalDate geboorteDatum;

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
    public User(String naam, String voornaam, LocalDate geboorteDatum, String email) {
        this.naam = naam;
        this.voornaam = voornaam;
        this.geboorteDatum = geboorteDatum;
        this.email = email;
    }

    // Getters and Setters

    public void setId(Long id){
        this.id = id;
    }
    public Long getId() {
        return id;
    }

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

    public List<Reservatie> getReservaties() {
        return reservaties;
    }
    public void setReservaties(List<Reservatie> reservaties) {
        this.reservaties = reservaties;
    }
}
