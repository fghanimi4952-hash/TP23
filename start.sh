#!/bin/bash

# Script de démarrage pour le TP 23 : Migration Eureka vers Consul
# =================================================================

echo "🚀 Démarrage du TP 23 : Migration Eureka vers Consul"
echo "===================================================="
echo ""

# Vérifier si Docker est installé
if ! command -v docker &> /dev/null; then
    echo "❌ Docker n'est pas installé. Veuillez l'installer d'abord."
    exit 1
fi

# Vérifier si Docker Compose est installé
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose n'est pas installé. Veuillez l'installer d'abord."
    exit 1
fi

echo "✅ Docker et Docker Compose sont installés"
echo ""

# Demander à l'utilisateur quel mode il souhaite
echo "Choisissez un mode de démarrage :"
echo "1) Docker Compose (tous les services)"
echo "2) Consul uniquement (mode dev local)"
read -p "Votre choix (1 ou 2) : " choice

case $choice in
    1)
        echo ""
        echo "📦 Démarrage avec Docker Compose..."
        docker-compose up -d
        
        echo ""
        echo "⏳ Attente du démarrage des services..."
        sleep 10
        
        echo ""
        echo "✅ Services démarrés !"
        echo ""
        echo "📍 Interfaces disponibles :"
        echo "   - Consul UI: http://localhost:8500"
        echo "   - Gateway: http://localhost:8888"
        echo "   - Service Client: http://localhost:8081"
        echo "   - Service Voiture: http://localhost:8082"
        echo ""
        echo "📊 Vérifier les logs : docker-compose logs -f"
        echo "🛑 Arrêter les services : docker-compose down"
        ;;
    2)
        echo ""
        echo "🔍 Vérification de Consul..."
        
        if ! command -v consul &> /dev/null; then
            echo "❌ Consul n'est pas installé."
            echo "   Installation :"
            echo "   - macOS: brew install consul"
            echo "   - Linux: télécharger depuis https://www.consul.io/downloads"
            exit 1
        fi
        
        echo "✅ Consul est installé"
        echo ""
        echo "🚀 Démarrage de Consul en mode dev..."
        consul agent -dev
        
        echo ""
        echo "✅ Consul démarré !"
        echo "   Interface web: http://localhost:8500"
        echo ""
        echo "💡 Vous pouvez maintenant démarrer les services manuellement :"
        echo "   cd service-client && mvn spring-boot:run"
        ;;
    *)
        echo "❌ Choix invalide"
        exit 1
        ;;
esac
