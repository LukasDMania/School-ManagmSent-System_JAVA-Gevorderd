package com.campusmanagment.dto.response;

import com.campusmanagment.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class ReservatieResponseDTO {

    // Fields
    private LocalDateTime startTijdstip;
    private LocalDateTime eindTijdstip;
    private String userCommentaar;

    // Relations
    private UserResponseDTO userResponseDTO;
    private List<LokaalResponseDTO> lokalenResponseDTOs;

    // Constructors
    public ReservatieResponseDTO() {}
    public ReservatieResponseDTO(LocalDateTime startTijdstip, LocalDateTime eindTijdstip, String userCommentaar, UserResponseDTO userResponseDTO, List<LokaalResponseDTO> lokalenResponseDTOs) {
        this.startTijdstip = startTijdstip;
        this.eindTijdstip = eindTijdstip;
        this.userCommentaar = userCommentaar;
        this.userResponseDTO = userResponseDTO;
        this.lokalenResponseDTOs = lokalenResponseDTOs;
    }
    //  No UserResponseDTO
    public ReservatieResponseDTO(LocalDateTime startTijdstip, LocalDateTime eindTijdstip, String userCommentaar, List<LokaalResponseDTO> lokalenResponseDTOs) {
        this.startTijdstip = startTijdstip;
        this.eindTijdstip = eindTijdstip;
        this.userCommentaar = userCommentaar;
        this.lokalenResponseDTOs = lokalenResponseDTOs;
    }
    //  No LokalenResponseDTOs
    public ReservatieResponseDTO(LocalDateTime startTijdstip, LocalDateTime eindTijdstip, String userCommentaar, UserResponseDTO userResponseDTO) {
        this.startTijdstip = startTijdstip;
        this.eindTijdstip = eindTijdstip;
        this.userCommentaar = userCommentaar;
        this.userResponseDTO = userResponseDTO;
    }
    //  No UserResponseDTO and LokalenResponseDTOs
    public ReservatieResponseDTO(LocalDateTime startTijdstip, LocalDateTime eindTijdstip, String userCommentaar) {
        this.startTijdstip = startTijdstip;
        this.eindTijdstip = eindTijdstip;
        this.userCommentaar = userCommentaar;
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

    public UserResponseDTO getUserResponseDTO() {
        return userResponseDTO;
    }
    public void setUserResponseDTO(UserResponseDTO userResponseDTO) {
        this.userResponseDTO = userResponseDTO;
    }

    public List<LokaalResponseDTO> getLokalenResponseDTOs() {
        return lokalenResponseDTOs;
    }
    public void setLokalenResponseDTOs(List<LokaalResponseDTO> lokalenResponseDTOs) {
        this.lokalenResponseDTOs = lokalenResponseDTOs;
    }


    //Override Methods
    @Override
    public String toString() {
        return "ReservatieResponseDTO{" +
                "startTijdstip='" + startTijdstip + '\'' +
                ", eindTijdstip='" + eindTijdstip + '\'' +
                ", userCommentaar='" + userCommentaar + '\'' +
                ", userResponseDTO=" + userResponseDTO +
                ", lokalenResponseDTOs=" + lokalenResponseDTOs +
                '}';
    }
}
