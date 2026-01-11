package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Classe principale du service Gateway
 * 
 * Ce service agit comme point d'entrée unique (API Gateway) pour tous les microservices.
 * Il route les requêtes vers les services appropriés en utilisant Consul pour la découverte.
 * 
 * Fonctionnalités :
 * - Routage dynamique basé sur les services découverts via Consul
 * - Load balancing automatique entre instances de services
 * - Point d'entrée unique pour les clients externes
 * 
 * @EnableDiscoveryClient : Active la découverte Consul pour le Gateway
 */
@SpringBootApplication
@EnableDiscoveryClient  // Active l'intégration avec Consul
public class GatewayServiceApplication {

    /**
     * Point d'entrée de l'application Gateway
     * 
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
