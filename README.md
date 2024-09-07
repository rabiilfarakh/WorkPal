# WorkPal

## Introduction

Avec l'essor du télétravail et des environnements de travail flexibles, le marché des espaces de coworking connaît une croissance rapide. WorkPal est une plateforme conçue pour aider les gestionnaires d'espaces de coworking à gérer efficacement les réservations, les membres et les services associés. Cette application vise à optimiser les opérations, améliorer l'expérience utilisateur et fournir des outils d'analyse et de reporting.

## Objectifs du Projet

- **Optimisation des Opérations** : Créer une plateforme centralisée pour la gestion des espaces, des réservations et des services.
- **Amélioration de l'Expérience Utilisateur** : Offrir une interface conviviale pour la réservation d'espaces, la gestion des abonnements et le paiement en ligne.
- **Analyse et Reporting** : Fournir des outils de reporting et d'analyse pour aider les gestionnaires à prendre des décisions informées.
- **Scalabilité et Flexibilité** : Concevoir une solution scalable pour s'adapter à la croissance des espaces de coworking.

## Fonctionnalités

## Diagrammes

### Use case 

![Use case diagram (1)](https://github.com/user-attachments/assets/1c2953fd-8d22-4e67-afc3-e02811593137)
![Use case diagram (2)](https://github.com/user-attachments/assets/c3ba7f34-622e-4a94-a112-30cc291cd045)
![Use case diagram (4)](https://github.com/user-attachments/assets/8cca497a-448c-49ea-b107-7d7669240408)


### Pour les Membres

- **Inscription et Connexion**
  - Création de compte avec informations personnelles.
  - Connexion avec adresse e-mail et mot de passe.
  - Récupération du mot de passe oublié.

- **Gestion du Profil**
  - Mise à jour des informations personnelles.
  - Ajout d'une photo de profil (optionnel).

- **Réservation d’Espaces**
  - Recherche d'espaces disponibles.
  - Réservation en ligne et confirmation.
  - Consultation des détails des espaces réservés.
  - Sauvegarde des espaces favoris.
  - Consultation du calendrier des événements et ateliers.

- **Consultation et Gestion des Réservations**
  - Historique des réservations passées et futures.
  - Annulation des réservations.

- **Gestion des Abonnements**
  - Choix et souscription à des plans d'abonnement.
  - Renouvellement, mise à jour ou annulation d'abonnement.
  - Consultation de l'historique des abonnements et des factures.

- **Accès aux Services Supplémentaires**
  - Réservation de services supplémentaires.

- **Notifications**
  - Notifications par e-mail pour les confirmations, rappels et changements.

- **Feedback et Évaluations**
  - Avis et notation des espaces utilisés.

### Pour les Gestionnaires d'Espaces

- **Gestion des Espaces**
  - Ajout, modification et suppression d'espaces.

- **Gestion des Réservations**
  - Consultation des réservations.
  - Annulation ou modification des réservations.

- **Gestion des Membres**
  - Ajout, modification ou suppression de comptes membres.
  - Attribution des niveaux d'accès.

- **Gestion des Abonnements et Facturations**
  - Création et gestion des plans d'abonnement.
  - Génération des factures et consultation de l'historique des paiements.
  - Configuration des alertes pour paiements et abonnements expirés.

- **Gestion des Services Supplémentaires**
  - Création, modification, ou suppression de services supplémentaires.

- **Rapports et Statistiques**
  - Génération de rapports sur l'utilisation des espaces et les revenus.
  - Consultation des statistiques d'occupation des espaces.

- **Gestion des Paiements**
  - Visualisation des paiements effectués et gestion des factures.

- **Notifications**
  - Notifications pour nouvelles réservations, annulations et modifications.

### Pour les Administrateurs

- **Gestion des Utilisateurs**
  - Ajout, modification ou suppression des comptes gestionnaires et membres.
  - Réinitialisation des mots de passe des utilisateurs.

- **Configuration de l'Application**
  - Configuration des paramètres globaux de l'application.
  - Gestion des paramètres de notifications (e-mail).

- **Gestion des Accès** (Bonus)
  - Attribution des rôles et permissions aux gestionnaires et membres.

## Exigences Techniques

- **Langages et Technologies** :
  - **Stream**
  - **Collection**
  - **Hashmap**
  - **Optional**
  - **Java Time**
  - **PostgreSQL** (base de données)

- **Design Patterns** :
  - **Singleton**
  - **Repository Pattern**

- **Architecture** :
  - Application organisée en couches
  - Validation des données

## Installation

1. **Cloner le dépôt :**
   ```bash
   git clone https://github.com/rabiilfarakh/workpal.git
