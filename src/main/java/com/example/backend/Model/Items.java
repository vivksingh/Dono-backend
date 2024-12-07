package com.example.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String category;
    private int quantity;
    private String message;
    @JsonIgnore
    @Lob
    private byte[] image;
    private String imageName;
    private String imageType;
    private String name;
    private String pickupDetails;
    private boolean isClaimed;
    private String location;
}
 