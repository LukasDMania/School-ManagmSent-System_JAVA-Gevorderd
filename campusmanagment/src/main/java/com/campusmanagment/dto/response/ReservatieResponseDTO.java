package com.campusmanagment.dto.response;

import com.campusmanagment.model.User;

import java.util.List;

public class ReservatieResponseDTO {

    // Fields
    private String reservatieNaam;
    private String startTijdstip;
    private String eindTijdstip;
    private String userCommentaar;

    // Relations
    private UserResponseDTO userResponseDTO;
    private List<LokaalResponseDTO> lokalenResponseDTOs;

    // Constructors
    public ReservatieResponseDTO() {}
    public ReservatieResponseDTO(String reservatieNaam, String startTijdstip, String eindTijdstip, String userCommentaar, UserResponseDTO userResponseDTO, List<LokaalResponseDTO> lokalenResponseDTOs) {
        this.reservatieNaam = reservatieNaam;
        this.startTijdstip = startTijdstip;
        this.eindTijdstip = eindTijdstip;
        this.userCommentaar = userCommentaar;
        this.userResponseDTO = userResponseDTO;
        this.lokalenResponseDTOs = lokalenResponseDTOs;
    }
    //  No UserResponseDTO
    public ReservatieResponseDTO(String reservatieNaam, String startTijdstip, String eindTijdstip, String userCommentaar, List<LokaalResponseDTO> lokalenResponseDTOs) {
        this.reservatieNaam = reservatieNaam;
        this.startTijdstip = startTijdstip;
        this.eindTijdstip = eindTijdstip;
        this.userCommentaar = userCommentaar;
        this.lokalenResponseDTOs = lokalenResponseDTOs;
    }
    //  No LokalenResponseDTOs
    public ReservatieResponseDTO(String reservatieNaam, String startTijdstip, String eindTijdstip, String userCommentaar, UserResponseDTO userResponseDTO) {
        this.reservatieNaam = reservatieNaam;
        this.startTijdstip = startTijdstip;
        this.eindTijdstip = eindTijdstip;
        this.userCommentaar = userCommentaar;
        this.userResponseDTO = userResponseDTO;
    }
    //  No UserResponseDTO and LokalenResponseDTOs
    public ReservatieResponseDTO(String reservatieNaam, String startTijdstip, String eindTijdstip, String userCommentaar) {
        this.reservatieNaam = reservatieNaam;
        this.startTijdstip = startTijdstip;
        this.eindTijdstip = eindTijdstip;
        this.userCommentaar = userCommentaar;
    }

    // Getters and Setters
    public String getReservatieNaam() {
        return reservatieNaam;
    }
    public void setReservatieNaam(String reservatieNaam) {
        this.reservatieNaam = reservatieNaam;
    }

    public String getStartTijdstip() {
        return startTijdstip;
    }
    public void setStartTijdstip(String startTijdstip) {
        this.startTijdstip = startTijdstip;
    }

    public String getEindTijdstip() {
        return eindTijdstip;
    }
    public void setEindTijdstip(String eindTijdstip) {
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
                "reservatieNaam='" + reservatieNaam + '\'' +
                ", startTijdstip='" + startTijdstip + '\'' +
                ", eindTijdstip='" + eindTijdstip + '\'' +
                ", userCommentaar='" + userCommentaar + '\'' +
                ", userResponseDTO=" + userResponseDTO +
                ", lokalenResponseDTOs=" + lokalenResponseDTOs +
                '}';
    }
}
