package org.example.view;

import org.example.entity.Manager;
import org.example.repository.impl.ManagerRepositoryImpl;
import org.example.repository.inter.ManagerRepository;
import org.example.service.impl.ManagerServiceImpl;
import org.example.service.inter.ManagerService;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
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
            System.out.println("🛠️ Menu Gestion des Managers 🛠️");
            System.out.println("1️⃣ Ajouter un manager 👤");
            System.out.println("2️⃣ Mettre à jour un manager ✏️");
            System.out.println("3️⃣ Supprimer un manager 🗑️");
            System.out.println("4️⃣ Afficher tous les managers 📋");
            System.out.println("5️⃣ Rechercher un manager par ID 🔍");
            System.out.println("6️⃣ Rechercher un manager par email 📧");
            System.out.println("7️⃣ Quitter 🚪");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Nettoyer le scanner

            switch (choice) {
                case 1:
                    System.out.print("Entrez le nom d'utilisateur 👤 : ");
                    String registerUsername = scanner.nextLine();
                    System.out.print("Entrez l'email 📧 : ");
                    String registerEmail = scanner.nextLine();
                    System.out.print("Entrez le mot de passe 🔑 : ");
                    String registerPassword = scanner.nextLine();

                    // Créez le manager
                    Manager newManager = new Manager();
                    newManager.setUser_name(registerUsername);
                    newManager.setEmail(registerEmail);
                    newManager.setPassword(registerPassword);

                    // Enregistrez le manager
                    managerService.register(newManager);
                    System.out.println("✅ Manager ajouté avec succès !");
                    break;

                case 2:
                    System.out.print("Entrez l'ID du manager à mettre à jour : ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Nettoyer le scanner

                    // Récupérer le manager existant
                    Optional<Manager> optionalManagerToUpdate = managerService.findById(updateId);
                    if (optionalManagerToUpdate.isPresent()) {
                        Manager updateManager = optionalManagerToUpdate.get();
                        System.out.print("Entrez le nouveau nom d'utilisateur 👤 : ");
                        String updateUsername = scanner.nextLine();
                        System.out.print("Entrez le nouvel email 📧 : ");
                        String updateEmail = scanner.nextLine();
                        System.out.print("Entrez le nouveau mot de passe 🔑 : ");
                        String updatePassword = scanner.nextLine();

                        // Mettre à jour les informations du manager
                        updateManager.setUser_name(updateUsername);
                        updateManager.setEmail(updateEmail);
                        updateManager.setPassword(updatePassword);

                        // Enregistrez les changements
                        managerService.update(updateManager);
                        System.out.println("✅ Manager mis à jour avec succès !");
                    } else {
                        System.out.println("❌ Manager non trouvé.");
                    }
                    break;

                case 3:
                    System.out.print("Entrez l'ID du manager à supprimer 🗑️ : ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine(); // Nettoyer le scanner

                    // Supprimer le manager
                    managerService.deleteById(deleteId);
                    System.out.println("✅ Manager supprimé avec succès !");
                    break;

                case 4:
                    // Afficher tous les managers
                    List<Manager> managers = managerService.findAll();
                    if (managers.isEmpty()) {
                        System.out.println("📋 Aucun manager trouvé.");
                    } else {
                        for (Manager manager : managers) {
                            System.out.println("ID: " + manager.getUser_id() +
                                    ", Nom d'utilisateur: " + manager.getUser_name() +
                                    ", Email: " + manager.getEmail());
                        }
                    }
                    break;

                case 5:
                    System.out.print("Entrez l'ID du manager à rechercher 🔍 : ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine(); // Nettoyer le scanner

                    Optional<Manager> optionalManagerById = managerService.findById(searchId);
                    if (optionalManagerById.isPresent()) {
                        Manager managerById = optionalManagerById.get();
                        System.out.println("ID: " + managerById.getUser_id() +
                                ", Nom d'utilisateur: " + managerById.getUser_name() +
                                ", Email: " + managerById.getEmail());
                    } else {
                        System.out.println("❌ Manager avec cet ID non trouvé.");
                    }
                    break;

                case 6:
                    System.out.print("Entrez l'email du manager à rechercher 📧 : ");
                    String searchEmail = scanner.nextLine();

                    Optional<Manager> optionalManagerByEmail = managerService.findByEmail(searchEmail);
                    if (optionalManagerByEmail.isPresent()) {
                        Manager managerByEmail = optionalManagerByEmail.get();
                        System.out.println("ID: " + managerByEmail.getUser_id() +
                                ", Nom d'utilisateur: " + managerByEmail.getUser_name() +
                                ", Email: " + managerByEmail.getEmail());
                    } else {
                        System.out.println("❌ Manager avec cet email non trouvé.");
                    }
                    break;

                case 7:
                    System.out.println("👋 Au revoir !");
                    System.exit(0);
                    break;

                default:
                    System.out.println("❌ Choix invalide. Veuillez réessayer.");
                    break;
            }
        }
    }
}
