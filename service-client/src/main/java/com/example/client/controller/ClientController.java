package com.example.client.controller;

import com.example.client.entity.Client;
import com.example.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des clients
 * 
 * Ce contrôleur expose les endpoints REST pour :
 * - Lister tous les clients
 * - Récupérer un client par son ID
 * - Créer un nouveau client
 * - Mettre à jour un client
 * - Supprimer un client
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

    /**
     * Injection du repository pour l'accès aux données
     */
    @Autowired
    private ClientRepository clientRepository;

    /**
     * GET /clients : Récupère tous les clients
     * 
     * @return Liste de tous les clients
     */
    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    /**
     * GET /clients/{id} : Récupère un client par son ID
     * 
     * @param id Identifiant du client
     * @return Le client trouvé ou 404 si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /clients : Crée un nouveau client
     * 
     * @param client Le client à créer (dans le body de la requête)
     * @return Le client créé avec son ID généré
     */
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    /**
     * PUT /clients/{id} : Met à jour un client existant
     * 
     * @param id Identifiant du client à mettre à jour
     * @param clientDetails Les nouvelles informations du client
     * @return Le client mis à jour ou 404 si non trouvé
     */
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setNom(clientDetails.getNom());
            client.setPrenom(clientDetails.getPrenom());
            client.setAge(clientDetails.getAge());
            client.setEmail(clientDetails.getEmail());
            return ResponseEntity.ok(clientRepository.save(client));
        }
        
        return ResponseEntity.notFound().build();
    }

    /**
     * DELETE /clients/{id} : Supprime un client
     * 
     * @param id Identifiant du client à supprimer
     * @return 204 (No Content) si supprimé, 404 si non trouvé
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
