package com.example.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Classe principale du service Client
 * 
 * Cette application Spring Boot expose un microservice REST pour la gestion des clients.
 * Elle utilise Consul pour la découverte de services (remplacement de Eureka).
 * 
 * @EnableDiscoveryClient : Active la découverte de services via Consul
 *                         Cette annotation permet au service de :
 *                         - S'enregistrer automatiquement dans Consul au démarrage
 *                         - Découvrir d'autres services enregistrés dans Consul
 *                         - Se désenregistrer proprement à l'arrêt
 */
@SpringBootApplication
@EnableDiscoveryClient  // IMPORTANT : Active l'intégration avec Consul (remplace @EnableEurekaClient)
public class ClientServiceApplication {

    /**
     * Point d'entrée de l'application
     * 
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }
}
