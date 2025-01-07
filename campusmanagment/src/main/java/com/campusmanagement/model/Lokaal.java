package com.campusmanagement.model;

import com.campusmanagement.util.LokaalType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table()
public class Lokaal {

    //Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Lokaal naam mag niet leeg zijn")
    @Size(max = 100, message = "Lokaal naam moet korter zijn dan 100 tekens")
    @Column(name = "lokaal_naam", nullable = false, length = 100)
    private String lokaalNaam;

    @NotNull(message = "Lokaal type moet worden opgegeven")
    @Enumerated(EnumType.STRING)
    @Column(name = "lokaal_type", nullable = false)
    private LokaalType lokaalType;

    @Positive(message = "Capaciteit moet een positief getal zijn")
    @Max(value = 1000, message = "Capaciteit mag niet meer dan 1000 zijn")
    @Column(name = "capaciteit", nullable = false)
    private int capaciteit;

    @Min(value = -100, message = "Verdieping moet tussen -100 en 100 liggen")
    @Max(value = 100, message = "Verdieping moet tussen -100 en 100 liggen")
    @Column(name = "verdieping", nullable = false)
    private int verdieping;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id", nullable = false)
    @NotNull(message = "Campus moet worden opgegeven")
    private Campus campus;

    @ManyToMany(mappedBy = "lokalen", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reservatie> reservaties;

    // Constructors
    public Lokaal(){};
    public Lokaal(String lokaalNaam, LokaalType lokaalType, int capaciteit, int verdieping){
        setLokaalNaam(lokaalNaam);
        setLokaalType(lokaalType);
        setCapaciteit(capaciteit);
        setVerdieping(verdieping);
    }

    // Getters and Setters
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

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

    public Campus getCampus() {
        return campus;
    }
    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    // Override Methods
    @Override
    public String toString() {
        return "Lokaal{" +
                "id=" + id +
                ", lokaalNaam='" + lokaalNaam + '\'' +
                ", lokaalType=" + lokaalType +
                ", capaciteit=" + capaciteit +
                ", verdieping=" + verdieping +
                ", campus=" + campus +
                '}';
    }
}

