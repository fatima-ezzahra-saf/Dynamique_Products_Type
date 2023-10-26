package com.example.prj2prod_type.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Type" , uniqueConstraints = {@UniqueConstraint(columnNames = "nom")})
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Type(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Type() {
    }
}
