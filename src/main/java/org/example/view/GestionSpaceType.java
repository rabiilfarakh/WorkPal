package org.example.view;

import org.example.entity.SpaceType;
import org.example.repository.impl.SpaceTypeRepositoryImpl;
import org.example.repository.inter.SpaceTypeRepository;
import org.example.service.impl.SpaceTypeServiceImpl;
import org.example.service.inter.SpaceTypeService;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GestionSpaceType {

    private final SpaceTypeService spaceTypeService;
    private final Scanner scanner;

    public GestionSpaceType(Connection connection) {
        SpaceTypeRepository spaceTypeRepository = new SpaceTypeRepositoryImpl(connection);
        this.spaceTypeService = new SpaceTypeServiceImpl(spaceTypeRepository);
        this.scanner = new Scanner(System.in); // Initialise le scanner ici
    }

    public void displaySpaceTypeMenu() {
        while (true) {
            System.out.println("🔧 Gestion des types d'espace :");
            System.out.println("1️⃣ Créer un type d'espace");
            System.out.println("2️⃣ Voir tous les types d'espace");
            System.out.println("3️⃣ Modifier un type d'espace");
            System.out.println("4️⃣ Supprimer un type d'espace");
            System.out.println("5️⃣ Quitter");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createSpaceType();
                    break;
                case 2:
                    viewAllSpaceTypes();
                    break;
                case 3:
                    updateSpaceType();
                    break;
                case 4:
                    deleteSpaceType();
                    break;
                case 5:
                    System.out.println("👋 Retour au menu principal.");
                    return;
                default:
                    System.out.println("⚠️ Choix invalide, réessayez.");
                    break;
            }
        }
    }

    // Méthode pour créer un nouveau type d'espace
    private void createSpaceType() {
        System.out.println("Entrez le nom du type d'espace : ");
        String name = scanner.nextLine();
        System.out.println("Entrez la politique associée : ");
        String politic = scanner.nextLine();

        SpaceType spaceType = new SpaceType();
        spaceType.setName(name);
        spaceType.setPolitic(politic);

        spaceTypeService.save(spaceType); // Utilise l'interface service pour sauvegarder
        System.out.println("✅ Type d'espace créé avec succès !");
    }

    // Méthode pour afficher tous les types d'espace
    private void viewAllSpaceTypes() {
        List<SpaceType> spaceTypes = spaceTypeService.findAll();
        if (spaceTypes.isEmpty()) {
            System.out.println("⚠️ Aucun type d'espace trouvé.");
        } else {
            System.out.println("📄 Liste des types d'espace :");
            spaceTypes.forEach(spaceType ->
                    System.out.println("ID: " + spaceType.getSpaceType_id() + " | Nom: " + spaceType.getName() + " | Politique: " + spaceType.getPolitic()));
        }
    }

    // Méthode pour modifier un type d'espace existant
    private void updateSpaceType() {
        System.out.println("Entrez l'ID du type d'espace à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Optional<SpaceType> optionalSpaceType = spaceTypeService.findById(id);
        if (optionalSpaceType.isPresent()) {
            SpaceType spaceType = optionalSpaceType.get();
            System.out.println("Entrez le nouveau nom (actuel : " + spaceType.getName() + ") : ");
            String newName = scanner.nextLine();
            System.out.println("Entrez la nouvelle politique (actuelle : " + spaceType.getPolitic() + ") : ");
            String newPolitic = scanner.nextLine();

            spaceType.setName(newName);
            spaceType.setPolitic(newPolitic);

            spaceTypeService.update(spaceType); // Utilise l'interface service pour la mise à jour
            System.out.println("✅ Type d'espace mis à jour avec succès !");
        } else {
            System.out.println("⚠️ Aucun type d'espace trouvé avec cet ID.");
        }
    }

    // Méthode pour supprimer un type d'espace
    private void deleteSpaceType() {
        System.out.println("Entrez l'ID du type d'espace à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean isDeleted = spaceTypeService.deletById(id); // Utilise l'interface service pour la suppression
        if (isDeleted) {
            System.out.println("✅ Type d'espace supprimé avec succès !");
        } else {
            System.out.println("⚠️ Aucun type d'espace trouvé avec cet ID.");
        }
    }
}
