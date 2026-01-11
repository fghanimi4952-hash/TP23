package com.example.client.repository;

import com.example.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour les opérations sur la base de données des clients
 * 
 * Spring Data JPA fournit automatiquement les méthodes CRUD standard :
 * - save(), findById(), findAll(), delete(), etc.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Des méthodes personnalisées peuvent être ajoutées ici si nécessaire
}
