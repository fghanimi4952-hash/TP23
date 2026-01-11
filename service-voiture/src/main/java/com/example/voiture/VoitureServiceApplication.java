package com.example.voiture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Classe principale du service Voiture
 * 
 * Cette application Spring Boot expose un microservice REST pour la gestion des voitures.
 * Elle utilise Consul pour la découverte de services (remplacement de Eureka).
 * 
 * @EnableDiscoveryClient : Active la découverte de services via Consul
 */
@SpringBootApplication
@EnableDiscoveryClient  // IMPORTANT : Active l'intégration avec Consul (remplace @EnableEurekaClient)
public class VoitureServiceApplication {

    /**
     * Point d'entrée de l'application
     * 
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(VoitureServiceApplication.class, args);
    }
}
