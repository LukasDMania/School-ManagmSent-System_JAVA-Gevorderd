package com.campusmanagment.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Adres {
    private String straat;
    private String stad;
    private String postcode;


    // Constructors
    public Adres () {
    }
    public Adres (String straat, String stad, String postcode) {
        this.straat = straat;
        this.stad = stad;
        this.postcode = postcode;
    }


    // Getters and Setters

    // Straat
    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    // Stad
    public String getStad() {
        return stad;
    }

    public void setStad(String stad) {
        this.stad = stad;
    }

    // Postcode
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    // ToString method
    @Override
    public String toString() {
        return String.format("%s, %s %s", straat, postcode, stad);
    }
}
