package com.campusmanagment.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Campus {

    // Fields
    @Id
    @Column(name = "campus_name", nullable = false)
    @NotBlank(message = "Campus naam mag niet leeg zijn")
    @Size(min = 3, max = 255, message = "Campus naam moet tussen de 3 en 255 characters zijn")
    private String campusNaam;

    @Embedded
    @Valid
    @NotNull(message = "Adres moet worden opgegeven")
    private Adres adres;

    @Column(name = "parkeerplaatsen", nullable = false)
    @Positive(message = "Parkeerplaatsen kan geen negatief nummer zijn")
    @Max(value = 10000, message = "Parkeerplaatsen kan niet groter zijn dan 10000")
    private int parkeerplaatsen;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", updatable = false)
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "campus",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Lokaal> lokalen;

    // Constructors
    public Campus() {}
    public Campus(String name, Adres adres, int parkeerplaatsen) {
        this.campusNaam = name;
        this.adres = adres;
        this.parkeerplaatsen = parkeerplaatsen;
    }
    public Campus(boolean initializeLokalen){
        if (initializeLokalen){
            lokalen = new ArrayList<>();
        }
    }
    public Campus(String name, Adres adres, int parkeerplaatsen, boolean initializeLokalen) {
        this.campusNaam = name;
        this.adres = adres;
        this.parkeerplaatsen = parkeerplaatsen;
        if (initializeLokalen){
            lokalen = new ArrayList<>();
        }
    }

    // Getters and Setters
    public String getCampusNaam() {
        return campusNaam;
    }
    public void setCampusNaam(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Campus naam kan niet null of empty zijn");
        }
        this.campusNaam = name.trim();
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
        return Collections.unmodifiableList(lokalen);
    }
    public void setLokalen(List<Lokaal> lokalen) {
        this.lokalen.clear();
        if (lokalen != null) {
            this.lokalen.addAll(lokalen);
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Business Logic Methods
    public int getAantalLokalen() {
        return lokalen == null ? 0 : lokalen.size();
    }

    public void addLokaal(Lokaal lokaal) {
        if (lokaal != null && !lokalen.contains(lokaal)) {
                lokalen.add(lokaal);
                lokaal.setCampus(this);
        }
    }

    public void removeLokaal(Lokaal lokaal) {
        if (lokaal != null) {
            lokalen.remove(lokaal);
            lokaal.setCampus(null);
        }
    }

    // Override Methods
    @Override
    public String toString() {
        return "Campus{" +
                "name='" + campusNaam + '\'' +
                ", parkeerplaatsen=" + parkeerplaatsen +
                ", adres=" + adres +
                ", aantal lokalen=" + getAantalLokalen() +
                '}';
    }
}
