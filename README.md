# TP 23 : Migration de Eureka vers Consul

##  Objectifs

- Comprendre la logique d'une migration de service discovery (Eureka → Consul)
- Configurer Consul pour enregistrer et découvrir des microservices
- Conteneuriser et déployer l'ensemble avec Docker et Docker Compose

##  Ce qui sera obtenu à la fin

-  Consul exécuté localement et accessible via son interface Web
-  Les microservices démarrent et se déclarent dans Consul
-  La découverte se fait via Consul (au lieu d'Eureka)
-  Une base solide pour conteneuriser le tout avec Docker Compose

##  Prérequis

- Docker et Docker Compose installés
- Java 11+ et Maven installés
- Git installé
- Un IDE (IntelliJ IDEA, Eclipse, VS Code)

##  Démarrage rapide avec Docker Compose

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

##  Démarrage manuel (mode développement local)

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

##  Vérification

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

##  Structure du projet


<img width="480" height="673" alt="Capture d’écran 2026-01-11 à 13 02 07" src="https://github.com/user-attachments/assets/e84991c2-a417-41e1-9bf2-f1ee5b0d2e97" />

## Résultat : 
# serveur Eureka 
<img width="653" height="386" alt="Capture d’écran 2026-01-11 à 13 03 26" src="https://github.com/user-attachments/assets/44dd9bc2-455e-4b3d-be4b-dfbde9bcbb50" />

# l’interface Web de Consul

<img width="653" height="258" alt="Capture d’écran 2026-01-11 à 13 04 56" src="https://github.com/user-attachments/assets/340581ed-e4e5-4b4f-bff2-34f51d3b767d" />


<img width="645" height="258" alt="Capture d’écran 2026-01-11 à 13 05 26" src="https://github.com/user-attachments/assets/96fc7dc3-ae2a-4cc1-ae8c-8b7cce8f68b7" />


