package org.example.util;

import org.example.entity.Manager;
import org.example.repository.impl.ManagerRepositoryImpl;
import org.example.repository.inter.ManagerRepository;
import org.example.service.impl.ManagerServiceImpl;
import org.example.service.inter.ManagerService;

import java.sql.Connection;
import java.util.Scanner;

public class GestionManager {

    private final ManagerService managerService;

    // Constructeur qui initialise le service du manager
    public GestionManager(Connection connection) {
        // Créer une instance du repository et du service pour gérer les managers
        ManagerRepository managerRepository = new ManagerRepositoryImpl(connection);
        this.managerService = new ManagerServiceImpl(managerRepository);
    }

    public void displayManagerMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choisissez une option :");
            System.out.println("1. Ajouter un manager");
            System.out.println("2. Mettre à jour un manager");
            System.out.println("3. Supprimer un manager");
            System.out.println("4. Afficher tous les managers");
            System.out.println("5. Quitter");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Nettoyer le scanner

            switch (choice) {
                case 1:
                    System.out.print("Entrez le nom d'utilisateur : ");
                    String registerUsername = scanner.nextLine();
                    System.out.print("Entrez l'email : ");
                    String registerEmail = scanner.nextLine();
                    System.out.print("Entrez le mot de passe : ");
                    String registerPassword = scanner.nextLine();

                    // Créez le manager
                    Manager newManager = new Manager();
                    newManager.setUser_name(registerUsername);
                    newManager.setEmail(registerEmail);
                    newManager.setPassword(registerPassword);

                    // Enregistrez le manager
                    managerService.register(newManager);
                    break;

                case 2:
                    // Appel pour mettre à jour un manager
                    System.out.println("Mise à jour d'un manager...");
                    // Code pour mise à jour ici
                    break;

                case 3:
                    // Appel pour supprimer un manager
                    System.out.println("Suppression d'un manager...");
                    // Code pour suppression ici
                    break;

                case 4:
                    // Appel pour afficher tous les managers
                    System.out.println("Liste de tous les managers...");
                    // Code pour afficher tous les managers ici
                    break;

                case 5:
                    System.out.println("Au revoir !");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    break;
            }
        }
    }
}
