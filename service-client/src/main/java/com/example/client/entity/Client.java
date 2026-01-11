package com.example.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Entité Client : représente un client dans la base de données
 * 
 * Cette classe utilise Lombok pour réduire le code boilerplate :
 * - @Data : génère getters, setters, toString, equals, hashCode
 * - @NoArgsConstructor : génère un constructeur sans arguments
 * - @AllArgsConstructor : génère un constructeur avec tous les arguments
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client {

    /**
     * Identifiant unique du client (clé primaire)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom du client
     */
    private String nom;

    /**
     * Prénom du client
     */
    private String prenom;

    /**
     * Âge du client
     */
    private int age;

    /**
     * Email du client
     */
    private String email;
}
