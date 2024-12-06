package com.example.backend.Model;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;
    private String name;
    private String address;
    
    
    private String password;
    private String about;

    @JsonIgnore
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private Blob image;
    private String imageName;
    private String imageType;
    
    private int donated;
    private int received;
    private int blessings;
}
