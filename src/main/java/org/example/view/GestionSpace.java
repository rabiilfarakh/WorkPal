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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GestionSpace {

    private final SpaceService spaceService;
    private final SpaceTypeService spaceTypeService;
    private final ManagerService managerService;
    private final Scanner scanner;
    private final Manager currentManager;
    private final Connection connection;

    public GestionSpace(Connection connection, Manager currentManager) {
        SpaceRepository spaceRepository = new SpaceRepositoryImpl(connection);
        this.spaceService = new SpaceServiceImpl(spaceRepository);

        SpaceTypeRepository spaceTypeRepository = new SpaceTypeRepositoryImpl(connection);
        this.spaceTypeService = new SpaceTypeServiceImpl(spaceTypeRepository);

        ManagerRepository managerRepository = new ManagerRepositoryImpl(connection);
        this.managerService = new ManagerServiceImpl(managerRepository);

        this.scanner = new Scanner(System.in);
        this.currentManager = currentManager;
        this.connection = connection;
    }

    // Include the method mapRowToSpace in the GestionSpace class
    private Space mapRowToSpace(ResultSet resultSet) throws SQLException {
        Space space = new Space();
        space.setSpace_id(resultSet.getInt("space_id"));
        space.setName(resultSet.getString("name"));
        space.setLocation(resultSet.getString("location"));
        space.setCapacity(resultSet.getInt("capacity"));
        space.setPrice(resultSet.getInt("price"));
        space.setAvailable(resultSet.getBoolean("available"));

        // Create SpaceType object
        SpaceType spaceType = new SpaceType();
        spaceType.setName(resultSet.getString("space_type_name")); // Ensure that the column name matches
        space.setSpaceType(spaceType);

        return space;
    }


    public void displaySpaceMenu() {
        while (true) {
            System.out.println("🔧 Gestion des espaces :");
            System.out.println("1️⃣ Créer un espace");
            System.out.println("2️⃣ Voir tous les espaces");
            System.out.println("3️⃣ Voir un espace par ID"); // Add this line
            System.out.println("4️⃣ Modifier un espace");
            System.out.println("5️⃣ Supprimer un espace");
            System.out.println("6️⃣ Quitter");

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
                    viewSpaceById(); // Call the new method
                    break;
                case 4:
                    updateSpace();
                    break;
                case 5:
                    deleteSpace();
                    break;
                case 6:
                    System.out.println("👋 Retour au menu principal.");
                    return;
                default:
                    System.out.println("⚠️ Choix invalide, réessayez.");
                    break;
            }
        }
    }

    private void viewSpaceById() {
        System.out.println("Entrez l'ID de l'espace à afficher : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Space> optionalSpace = spaceService.findById(id);
        if (optionalSpace.isPresent()) {
            Space space = optionalSpace.get();
            System.out.println("📄 Détails de l'espace :");
            System.out.print("| ID: " + space.getSpace_id());
            System.out.print("| Nom: " + space.getName());
            System.out.print("| Localisation: " + space.getLocation());
            System.out.print("| Capacité: " + space.getCapacity());
            System.out.print("| Prix: " + space.getPrice());
            System.out.print("| Disponible: " + space.isAvailable());
            System.out.println("| Type d'espace: " + space.getSpaceType().getName());
        } else {
            System.out.println("⚠️ Aucun espace trouvé avec cet ID.");
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
            spaces.stream()
                    .forEach(space -> {
                        System.out.print("ID: " + space.getSpace_id());
                        System.out.print("| Name: " + space.getName());
                        System.out.print("| Location: " + space.getLocation());
                        System.out.print("| Capacity: " + space.getCapacity());
                        System.out.print("| Price: " + space.getPrice());
                        System.out.print("| Available: " + space.isAvailable());
                        System.out.println("| Space Type: " + space.getSpaceType().getName());
                       // System.out.println("Manager: " + space.getManager().getUser_name());
                    });

        }
    }

    private void updateSpace() {
        System.out.println("Entrez l'ID de l'espace à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Optional<Space> optionalSpace =spaceService.findById(id);
        if (optionalSpace.isPresent()) {
            Space space = optionalSpace.get();

            // Si le manager est nul, attribuez le manager actuel
            if (space.getManager() == null) {
                space.setManager(currentManager);
            }

            System.out.println("Entrez le nouveau nom (actuel : " + space.getName() + ") : ");
            String newName = scanner.nextLine().trim();
            if (!newName.isEmpty()) {
                space.setName(newName);
            }

            System.out.println("Entrez la nouvelle localisation (actuelle : " + space.getLocation() + ") : ");
            String newLocation = scanner.nextLine().trim();
            if (!newLocation.isEmpty()) {
                space.setLocation(newLocation);
            }

            System.out.println("Entrez la nouvelle capacité (actuelle : " + space.getCapacity() + ") : ");
            String capacityInput = scanner.nextLine().trim();
            if (!capacityInput.isEmpty()) {
                try {
                    int newCapacity = Integer.parseInt(capacityInput);
                    if (newCapacity > 0) {
                        space.setCapacity(newCapacity);
                    } else {
                        System.out.println("⚠️ La capacité doit être supérieure à zéro.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Valeur invalide pour la capacité.");
                }
            }

            System.out.println("Entrez le nouveau prix (actuel : " + space.getPrice() + ") : ");
            String priceInput = scanner.nextLine().trim();
            if (!priceInput.isEmpty()) {
                try {
                    int newPrice = Integer.parseInt(priceInput);
                    if (newPrice >= 0) {
                        space.setPrice(newPrice);
                    } else {
                        System.out.println("⚠️ Le prix doit être supérieur ou égal à zéro.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Valeur invalide pour le prix.");
                }
            }

            System.out.println("L'espace est-il disponible ? (actuel : " + space.isAvailable() + ") (true/false) : ");
            String availableInput = scanner.nextLine().trim();
            if (!availableInput.isEmpty()) {
                try {
                    boolean newAvailable = Boolean.parseBoolean(availableInput);
                    space.setAvailable(newAvailable);
                } catch (Exception e) {
                    System.out.println("⚠️ Valeur invalide pour la disponibilité.");
                }
            }

            // Liste des types d'espaces disponibles
            List<SpaceType> spaceTypes = spaceTypeService.findAll();
            if (!spaceTypes.isEmpty()) {
                System.out.println("Choisissez un nouveau type d'espace :");
                for (int i = 0; i < spaceTypes.size(); i++) {
                    System.out.println((i + 1) + ". " + spaceTypes.get(i).getName());
                }

                int typeChoice = scanner.nextInt();
                scanner.nextLine();

                if (typeChoice >= 1 && typeChoice <= spaceTypes.size()) {
                    SpaceType chosenSpaceType = spaceTypes.get(typeChoice - 1);
                    space.setSpaceType(chosenSpaceType);
                } else {
                    System.out.println("⚠️ Choix invalide pour le type d'espace.");
                }
            } else {
                System.out.println("⚠️ Aucun type d'espace disponible.");
            }

            // Mise à jour de l'espace dans la base de données
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
            System.out.println("⚠️ Échec de la suppression de l'espace.");
        }
    }
}
