package com.example.voiture.repository;

import com.example.voiture.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour les opérations sur la base de données des voitures
 */
@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    
    /**
     * Trouve toutes les voitures d'un client donné
     * 
     * @param clientId ID du client
     * @return Liste des voitures du client
     */
    List<Voiture> findByClientId(Long clientId);
}
