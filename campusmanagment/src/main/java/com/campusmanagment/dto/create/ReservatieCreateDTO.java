package com.campusmanagment.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class ReservatieCreateDTO {

    // Fields
    @NotNull(message = "Start tijdstip moet worden opgegeven")
    private LocalDateTime startTijdstip;

    @NotNull(message = "Eind tijdstip moet worden opgegeven")
    private LocalDateTime eindTijdstip;

    @Size(max = 255, message = "Commentaar mag niet langer zijn dan 255 karakters")
    private String userCommentaar;

    @NotNull(message = "User moet worden opgegeven")
    private Long userId;

    @NotNull(message = "Minstens 1 lokaal moet worden opgegeven")
    private List<Long> lokalenIds;

    // Constructors
    public ReservatieCreateDTO() {}
    public ReservatieCreateDTO(LocalDateTime startTijdstip, LocalDateTime eindTijdstip, String userCommentaar, Long userId, List<Long> lokalenIds) {
        this.startTijdstip = startTijdstip;
        this.eindTijdstip = eindTijdstip;
        this.userCommentaar = userCommentaar;
        this.userId = userId;
        this.lokalenIds = lokalenIds;
    }

    // Getters and Setters
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

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getLokalenIds() {
        return lokalenIds;
    }
    public void setLokalenIds(List<Long> lokalenIds) {
        this.lokalenIds = lokalenIds;
    }

    //Override Methods
    @Override
    public String toString() {
        return "ReservatieCreateDTO{" +
                "startTijdstip=" + startTijdstip +
                ", eindTijdstip=" + eindTijdstip +
                ", userCommentaar='" + userCommentaar + '\'' +
                ", userId=" + userId +
                ", lokalenIds=" + lokalenIds +
                '}';
    }
}
