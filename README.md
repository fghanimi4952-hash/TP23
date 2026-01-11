# TP 23 : Migration de Eureka vers Consul

## 📋 Objectifs

- Comprendre la logique d'une migration de service discovery (Eureka → Consul)
- Configurer Consul pour enregistrer et découvrir des microservices
- Conteneuriser et déployer l'ensemble avec Docker et Docker Compose

## 🎯 Ce qui sera obtenu à la fin

- ✅ Consul exécuté localement et accessible via son interface Web
- ✅ Les microservices démarrent et se déclarent dans Consul
- ✅ La découverte se fait via Consul (au lieu d'Eureka)
- ✅ Une base solide pour conteneuriser le tout avec Docker Compose

## 📦 Prérequis

- Docker et Docker Compose installés
- Java 11+ et Maven installés
- Git installé
- Un IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Démarrage rapide avec Docker Compose

### 1. Cloner et démarrer tous les services

```bash
# Démarrer tous les services (Consul, MySQL, Services)
docker-compose up -d

# Vérifier que tous les services sont démarrés
docker-compose ps

# Voir les logs
docker-compose logs -f
```

### 2. Accéder aux interfaces

- **Consul UI** : http://localhost:8500
- **Gateway** : http://localhost:8888
- **Service Client** : http://localhost:8081
- **Service Voiture** : http://localhost:8082

### 3. Arrêter les services

```bash
docker-compose down
```

## 🔧 Démarrage manuel (mode développement local)

### Étape 1 : Démarrer Consul

```bash
# Télécharger Consul depuis https://www.consul.io/downloads
# Ou utiliser Homebrew (macOS) :
brew install consul

# Lancer Consul en mode développement
consul agent -dev

# Consul sera accessible sur http://localhost:8500
```

### Étape 2 : Démarrer MySQL

```bash
# Option 1 : Utiliser Docker
docker run -d --name mysql-client -p 3309:3306 \
  -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=Micro_ClientDB \
  mysql:8.0

docker run -d --name mysql-voiture -p 3308:3306 \
  -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=Micro_VoitureDB \
  mysql:8.0

# Option 2 : Installer MySQL localement
# Créer les bases de données manuellement
```

### Étape 3 : Démarrer les microservices

Ouvrir 3 terminaux distincts :

```bash
# Terminal 1 : Service Client
cd service-client
mvn spring-boot:run

# Terminal 2 : Service Voiture
cd service-voiture
mvn spring-boot:run

# Terminal 3 : Service Gateway
cd service-gateway
mvn spring-boot:run
```

## ✅ Vérification

### 1. Vérifier l'enregistrement dans Consul

1. Ouvrir http://localhost:8500
2. Aller dans la section **Services**
3. Vérifier que les services suivants sont listés :
   - `SERVICE-CLIENT` (1 instance)
   - `SERVICE-VOITURE` (1 instance)
   - `GATEWAY-SERVICE` (1 instance)
4. Cliquer sur un service pour voir :
   - Nombre d'instances
   - État de santé (passing/warning/critical)
   - Adresse/port

### 2. Tester les API via le Gateway

```bash
# Créer un client
curl -X POST http://localhost:8888/api/client/clients \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Dupont",
    "prenom": "Jean",
    "age": 30,
    "email": "jean.dupont@example.com"
  }'

# Lister tous les clients
curl http://localhost:8888/api/client/clients

# Créer une voiture
curl -X POST http://localhost:8888/api/voiture/voitures \
  -H "Content-Type: application/json" \
  -d '{
    "marque": "Renault",
    "modele": "Clio",
    "matricule": "AB-123-CD",
    "clientId": 1
  }'

# Lister toutes les voitures
curl http://localhost:8888/api/voiture/voitures
```

### 3. Tester directement les services

```bash
# Service Client
curl http://localhost:8081/clients

# Service Voiture
curl http://localhost:8082/voitures
```

## 📁 Structure du projet

```
TP23/
├── service-client/          # Microservice Client
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   │       └── application.yml
│   └── pom.xml
├── service-gateway/         # API Gateway
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   │       └── application.yml
│   └── pom.xml
├── service-voiture/         # Microservice Voiture
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   │       └── application.yml
│   └── pom.xml
├── docker-compose.yml       # Configuration Docker Compose
└── README.md
```

## 🔄 Migration Eureka → Consul

### Changements principaux

1. **Dépendances Maven** :
   - ❌ Supprimer : `spring-cloud-starter-netflix-eureka-client`
   - ✅ Ajouter : `spring-cloud-starter-consul-discovery`

2. **Configuration (application.yml)** :
   - ❌ Supprimer : `eureka.client.*`
   - ✅ Ajouter : `spring.cloud.consul.*`

3. **Annotations** :
   - ❌ `@EnableEurekaClient` (déprécié)
   - ✅ `@EnableDiscoveryClient` (générique, fonctionne avec Consul)

4. **Interface Web** :
   - ❌ Eureka Dashboard
   - ✅ Consul UI (http://localhost:8500)

## 🎓 Points importants

### Consul vs Eureka

- **Consul** : Solution développée par HashiCorp
  - Découverte de services
  - Health checks avancés
  - Stockage Key/Value
  - Coordination distribuée

- **Eureka** : Solution Netflix
  - Focalisée sur la découverte
  - Très intégrée à Spring Cloud

### Configuration Consul

```yaml
spring:
  cloud:
    consul:
      host: localhost        # Adresse Consul
      port: 8500             # Port par défaut
      discovery:
        service-name: SERVICE-CLIENT  # Nom dans Consul
        enabled: true
        health-check-path: /actuator/health
```

### Avantages de la migration

- ✅ Health checks plus robustes
- ✅ Interface web moderne
- ✅ Support multi-datacenter
- ✅ Stockage Key/Value intégré
- ✅ Compatibilité avec d'autres technologies (pas seulement Java)

## 🐛 Dépannage

### Les services ne s'enregistrent pas dans Consul

1. Vérifier que Consul est démarré : `consul members`
2. Vérifier les logs des services : chercher les erreurs de connexion à Consul
3. Vérifier la configuration dans `application.yml` : host et port

### Erreurs de connexion à MySQL

1. Vérifier que MySQL est démarré
2. Vérifier le port dans `application.yml` (3309 pour Client, 3308 pour Voiture)
3. Vérifier les credentials (root/root)

### Le Gateway ne route pas correctement

1. Vérifier que les services sont enregistrés dans Consul
2. Vérifier les routes dans `application.yml` du Gateway
3. Vérifier les noms de services (doivent correspondre)

## 📚 Ressources

- [Documentation Consul](https://www.consul.io/docs)
- [Spring Cloud Consul](https://spring.io/projects/spring-cloud-consul)
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)

## 📝 Remarque de clôture

La migration de Eureka vers Consul permet d'améliorer la résilience et la gestion des microservices. En suivant ces étapes, les projets utilisent désormais Consul pour la découverte de services, offrant une base solide pour le déploiement en production.
