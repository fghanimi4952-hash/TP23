# Dockerfile pour le service Client
# ==================================

# Image de base : Maven avec OpenJDK 11
FROM maven:3.8.7-openjdk-11-slim AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml pour télécharger les dépendances (cache layer)
COPY pom.xml .

# Télécharger les dépendances (mise en cache si pom.xml ne change pas)
RUN mvn dependency:go-offline -B

# Copier le code source
COPY src ./src

# Construire l'application (package sans tests pour accélérer le build)
RUN mvn clean package -DskipTests

# Image runtime : OpenJDK 11 (plus légère que Maven)
FROM openjdk:11-jre-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR construit depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Exposer le port 8081
EXPOSE 8081

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
