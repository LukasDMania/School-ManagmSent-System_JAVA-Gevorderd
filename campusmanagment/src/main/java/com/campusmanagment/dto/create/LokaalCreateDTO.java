package com.campusmanagment.dto.create;

import com.campusmanagment.util.LokaalType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

public class LokaalCreateDTO {

    // Fields
    @NotBlank(message = "Lokaal naam mag niet leeg zijn")
    @Size(max = 100, message = "Lokaal naam moet korter zijn dan 100 tekens")
    private String lokaalNaam;

    @NotNull(message = "Lokaal type moet worden opgegeven")
    @Enumerated(EnumType.STRING)
    private LokaalType lokaalType;

    @Positive(message = "Capaciteit moet een positief getal zijn")
    @Max(value = 1000, message = "Capaciteit mag niet meer dan 1000 zijn")
    private int capaciteit;

    @Min(value = -100, message = "Verdieping moet tussen -100 en 100 liggen")
    @Max(value = 100, message = "Verdieping moet tussen -100 en 100 liggen")
    private int verdieping;

    @NotNull
    private Long campusId;

    // Constructors
    public LokaalCreateDTO() {}
    public LokaalCreateDTO(String lokaalNaam, LokaalType lokaalType, int capaciteit, int verdieping, Long campusId) {
        this.lokaalNaam = lokaalNaam;
        this.lokaalType = lokaalType;
        this.capaciteit = capaciteit;
        this.verdieping = verdieping;
        this.campusId = campusId;
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

    public Long getCampusId() {
        return campusId;
    }
    public void setCampusId(Long campusId) {
        this.campusId = campusId;
    }

    // Override Methods
    @Override
    public String toString() {
        return "LokaalCreateDTO{" +
                "lokaalNaam='" + lokaalNaam + '\'' +
                ", lokaalType=" + lokaalType +
                ", capaciteit=" + capaciteit +
                ", verdieping=" + verdieping +
                ", campusId=" + campusId +
                '}';
    }
}
