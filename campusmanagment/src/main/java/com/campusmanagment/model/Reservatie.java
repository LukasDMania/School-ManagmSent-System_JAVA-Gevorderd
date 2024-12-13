package com.campusmanagment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservaties")
public class Reservatie {

    //Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Start tijdstip moet worden opgegeven")
    private LocalDateTime startTijdstip;

    @NotNull(message = "Eind tijdstip moet worden opgegeven")
    private LocalDateTime eindTijdstip;

    @Size(max = 255, message = "Commentaar mag niet langer zijn dan 255 karakters")
    private String userCommentaar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id", nullable = false)
    @NotNull(message = "User moet worden opgegeven")
    private User user;

    // Many-to-Many relationship with Lokaal
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservatie_lokaal",
            joinColumns = @JoinColumn(name = "reservatie_id"),
            inverseJoinColumns = @JoinColumn(name = "lokaal_id")
    )
    private List<Lokaal> lokalen = new ArrayList<>();

    // Max capaciteit van de lokalen
    @Transient
    private int maxCapaciteit;

    // Constructors
    public Reservatie() {
    }
    public Reservatie(LocalDateTime startTijdstip, LocalDateTime eindTijdstip, String userCommentaar) {
        setStartTijdstip(startTijdstip);
        setEindTijdstip(eindTijdstip);
        setUserCommentaar(userCommentaar);
    }

    // Getters and Setters

    public void setId(Long id){
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTijdstip() {
        return startTijdstip;
    }
    public void setStartTijdstip(LocalDateTime startTijdstip) {
        this.startTijdstip = startTijdstip;
    }

    public LocalDateTime getEindTijdstip() {
        return eindTijdstip;
    }
    public void setEindTijdstip(LocalDateTime eindTijdstip) {
        this.eindTijdstip = eindTijdstip;
    }

    public String getUserCommentaar() {
        return userCommentaar;
    }
    public void setUserCommentaar(String userCommentaar) {
        this.userCommentaar = userCommentaar;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public List<Lokaal> getLokalen() {
        return lokalen;
    }
    public void setLokalen(List<Lokaal> lokalen) {
        this.lokalen = lokalen;
    }

    public int getMaxCapaciteit() {
        return berekenMaxCapaciteit();
    }
    public void setMaxCapaciteit(int maxCapaciteit) {
        this.maxCapaciteit = maxCapaciteit;
    }

    // Business logic
    public Integer berekenMaxCapaciteit() {
        if (lokalen != null) {
            return lokalen.stream()
                    .mapToInt(Lokaal::getCapaciteit)
                    .sum();
        }
        return 0;
    }
}
