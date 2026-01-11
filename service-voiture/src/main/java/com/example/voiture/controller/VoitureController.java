package com.example.voiture.controller;

import com.example.voiture.entity.Voiture;
import com.example.voiture.repository.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des voitures
 */
@RestController
@RequestMapping("/voitures")
public class VoitureController {

    @Autowired
    private VoitureRepository voitureRepository;

    /**
     * GET /voitures : Récupère toutes les voitures
     */
    @GetMapping
    public List<Voiture> getAllVoitures() {
        return voitureRepository.findAll();
    }

    /**
     * GET /voitures/{id} : Récupère une voiture par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Voiture> getVoitureById(@PathVariable Long id) {
        Optional<Voiture> voiture = voitureRepository.findById(id);
        return voiture.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /voitures/client/{clientId} : Récupère toutes les voitures d'un client
     */
    @GetMapping("/client/{clientId}")
    public List<Voiture> getVoituresByClientId(@PathVariable Long clientId) {
        return voitureRepository.findByClientId(clientId);
    }

    /**
     * POST /voitures : Crée une nouvelle voiture
     */
    @PostMapping
    public Voiture createVoiture(@RequestBody Voiture voiture) {
        return voitureRepository.save(voiture);
    }

    /**
     * PUT /voitures/{id} : Met à jour une voiture existante
     */
    @PutMapping("/{id}")
    public ResponseEntity<Voiture> updateVoiture(@PathVariable Long id, @RequestBody Voiture voitureDetails) {
        Optional<Voiture> optionalVoiture = voitureRepository.findById(id);
        
        if (optionalVoiture.isPresent()) {
            Voiture voiture = optionalVoiture.get();
            voiture.setMarque(voitureDetails.getMarque());
            voiture.setModele(voitureDetails.getModele());
            voiture.setMatricule(voitureDetails.getMatricule());
            voiture.setClientId(voitureDetails.getClientId());
            return ResponseEntity.ok(voitureRepository.save(voiture));
        }
        
        return ResponseEntity.notFound().build();
    }

    /**
     * DELETE /voitures/{id} : Supprime une voiture
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoiture(@PathVariable Long id) {
        if (voitureRepository.existsById(id)) {
            voitureRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
