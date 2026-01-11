package com.example.voiture.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Entité Voiture : représente une voiture dans la base de données
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voitures")
public class Voiture {

    /**
     * Identifiant unique de la voiture (clé primaire)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Marque de la voiture (ex: Renault, Peugeot, etc.)
     */
    private String marque;

    /**
     * Modèle de la voiture
     */
    private String modele;

    /**
     * Matricule (plaque d'immatriculation)
     */
    private String matricule;

    /**
     * ID du client propriétaire de la voiture
     */
    private Long clientId;
}
