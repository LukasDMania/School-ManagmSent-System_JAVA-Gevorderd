package com.campusmanagement.model;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Embeddable
public class Adres {

    // Fields
    @NotBlank(message = "Straat mag niet leeg zijn")
    @Size(max = 255, message = "Straat moet korter zijn dan 255 tekens")
    private String straat;

    @NotBlank(message = "Stad mag niet leeg zijn")
    @Size(max = 100, message = "Stad moet korter zijn dan 100 tekens")
    private String stad;

    @NotBlank(message = "Postcode mag niet leeg zijn")
    @Pattern(regexp = "^[0-9]{4}$", message = "Postcode moet 4 cijfers bevatten")
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
    public String getStraat() {
        return straat;
    }
    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getStad() {
        return stad;
    }
    public void setStad(String stad) {
        this.stad = stad;
    }

    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    // Override Methods
    @Override
    public String toString() {
        return "Adres{" +
                "straat='" + straat + '\'' +
                ", stad='" + stad + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
