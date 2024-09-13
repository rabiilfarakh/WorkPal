package org.example.view;

import org.example.entity.Manager;
import org.example.entity.Space;
import org.example.entity.SpaceType;
import org.example.repository.impl.ManagerRepositoryImpl;
import org.example.repository.impl.SpaceRepositoryImpl;
import org.example.repository.impl.SpaceTypeRepositoryImpl;
import org.example.repository.inter.ManagerRepository;
import org.example.repository.inter.SpaceRepository;
import org.example.repository.inter.SpaceTypeRepository;
import org.example.service.impl.ManagerServiceImpl;
import org.example.service.impl.SpaceServiceImpl;
import org.example.service.impl.SpaceTypeServiceImpl;
import org.example.service.inter.ManagerService;
import org.example.service.inter.SpaceService;
import org.example.service.inter.SpaceTypeService;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GestionSpace {

    private final SpaceService spaceService;
    private final SpaceTypeService spaceTypeService;
    private final ManagerService managerService;
    private final Scanner scanner;
    private final Manager currentManager; // Reference to the currently logged-in manager

    public GestionSpace(Connection connection, Manager currentManager) {
        SpaceRepository spaceRepository = new SpaceRepositoryImpl(connection);
        this.spaceService = new SpaceServiceImpl(spaceRepository);

        SpaceTypeRepository spaceTypeRepository = new SpaceTypeRepositoryImpl(connection);
        this.spaceTypeService = new SpaceTypeServiceImpl(spaceTypeRepository);

        ManagerRepository managerRepository = new ManagerRepositoryImpl(connection);
        this.managerService = new ManagerServiceImpl(managerRepository);

        this.scanner = new Scanner(System.in);
        this.currentManager = currentManager;
    }


    public void displaySpaceMenu() {
        while (true) {
            System.out.println("🔧 Gestion des espaces :");
            System.out.println("1️⃣ Créer un espace");
            System.out.println("2️⃣ Voir tous les espaces");
            System.out.println("3️⃣ Modifier un espace");
            System.out.println("4️⃣ Supprimer un espace");
            System.out.println("5️⃣ Quitter");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createSpace();
                    break;
                case 2:
                    viewAllSpaces();
                    break;
                case 3:
                    updateSpace();
                    break;
                case 4:
                    deleteSpace();
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

    private void createSpace() {
        System.out.println("Entrez le nom de l'espace : ");
        String name = scanner.nextLine();
        System.out.println("Entrez la localisation de l'espace : ");
        String location = scanner.nextLine();
        System.out.println("Entrez la capacité de l'espace : ");
        int capacity = scanner.nextInt();
        System.out.println("Entrez le prix de l'espace : ");
        int price = scanner.nextInt();
        System.out.println("L'espace est-il disponible ? (true/false) : ");
        boolean available = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        // Lister tous les types d'espace
        List<SpaceType> spaceTypes = spaceTypeService.findAll();
        if (spaceTypes.isEmpty()) {
            System.out.println("⚠️ Aucun type d'espace disponible.");
            return;
        }

        System.out.println("Veuillez choisir un type d'espace :");
        for (int i = 0; i < spaceTypes.size(); i++) {
            System.out.println((i + 1) + ". " + spaceTypes.get(i).getName());
        }

        int typeChoice = scanner.nextInt();
        scanner.nextLine();

        if (typeChoice < 1 || typeChoice > spaceTypes.size()) {
            System.out.println("⚠️ Choix invalide.");
            return;
        }

        SpaceType chosenSpaceType = spaceTypes.get(typeChoice - 1);

        // Use the currently logged-in manager
        Space space = new Space();
        space.setName(name);
        space.setLocation(location);
        space.setCapacity(capacity);
        space.setPrice(price);
        space.setAvailable(available);
        space.setManager(currentManager);
        space.setSpaceType(chosenSpaceType);

        spaceService.save(space);
        System.out.println("✅ Espace créé avec succès !");
    }

    private void viewAllSpaces() {
        List<Space> spaces = spaceService.findAll();
        if (spaces.isEmpty()) {
            System.out.println("⚠️ Aucun espace trouvé.");
        } else {
            System.out.println("📄 Liste des espaces :");
            spaces.forEach(space ->
                    System.out.println("ID: " + space.getSpace_id() + " | Nom: " + space.getName() +
                            " | Localisation: " + space.getLocation() + " | Capacité: " + space.getCapacity() +
                            " | Prix: " + space.getPrice() + " | Disponible: " + space.isAvailable()));
        }
    }

    private void updateSpace() {
        System.out.println("Entrez l'ID de l'espace à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Optional<Space> optionalSpace = spaceService.findById(id);
        if (optionalSpace.isPresent()) {
            Space space = optionalSpace.get();
            System.out.println("Entrez le nouveau nom (actuel : " + space.getName() + ") : ");
            String newName = scanner.nextLine();
            System.out.println("Entrez la nouvelle localisation (actuelle : " + space.getLocation() + ") : ");
            String newLocation = scanner.nextLine();
            System.out.println("Entrez la nouvelle capacité (actuelle : " + space.getCapacity() + ") : ");
            int newCapacity = scanner.nextInt();
            System.out.println("Entrez le nouveau prix (actuel : " + space.getPrice() + ") : ");
            int newPrice = scanner.nextInt();
            System.out.println("L'espace est-il disponible ? (true/false) : ");
            boolean newAvailable = scanner.nextBoolean();
            scanner.nextLine(); // Consume newline

            space.setName(newName);
            space.setLocation(newLocation);
            space.setCapacity(newCapacity);
            space.setPrice(newPrice);
            space.setAvailable(newAvailable);

            spaceService.update(space);
            System.out.println("✅ Espace mis à jour avec succès !");
        } else {
            System.out.println("⚠️ Aucun espace trouvé avec cet ID.");
        }
    }

    private void deleteSpace() {
        System.out.println("Entrez l'ID de l'espace à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean isDeleted = spaceService.deletById(id);
        if (isDeleted) {
            System.out.println("✅ Espace supprimé avec succès !");
        } else {
            System.out.println("⚠️ Aucun espace trouvé avec cet ID.");
        }
    }
}
