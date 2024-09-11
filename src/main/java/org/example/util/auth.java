package org.example.util;

import org.example.config.Database;
import org.example.entity.Member;
import org.example.enumeration.Role;
import org.example.entity.User;
import org.example.repository.impl.ManagerRepositoryImpl;
import org.example.repository.impl.MemberRepositoryImpl;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.repository.inter.ManagerRepository;
import org.example.repository.inter.MemberRepository;
import org.example.repository.inter.UserRepository;
import org.example.service.impl.ManagerServiceImpl;
import org.example.service.impl.MemberServiceImpl;
import org.example.service.impl.UserServiceImpl;
import org.example.service.inter.ManagerService;
import org.example.service.inter.MemberService;
import org.example.service.inter.UserService;
import org.example.smtp.SendMail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class auth {

    public static void handleUserOperations() {
        Connection connection = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Obtenez la connexion à la base de données
            Database db = Database.getInstance();
            connection = db.getConnection();

            // Créez les instances du repository et du service
            UserRepository userRepository = new UserRepositoryImpl(connection);
            UserService userService = new UserServiceImpl(userRepository);

            MemberRepository memberRepository = new MemberRepositoryImpl(connection);
            MemberService memberService = new MemberServiceImpl(memberRepository);

            ManagerRepository managerRepository = new ManagerRepositoryImpl(connection);
            ManagerService managerService = new ManagerServiceImpl(managerRepository);

            while (true) {
                // Affichez le menu des opérations utilisateur
                System.out.println("Choisissez une option :");
                System.out.println("1. S'inscrire");
                System.out.println("2. Se connecter");
                System.out.println("3. Réinitialiser le mot de passe");
                System.out.println("4. Quitter");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        // Inscription
                        System.out.print("Entrez username : ");
                        String registerUsername = scanner.nextLine();
                        System.out.print("Entrez l'email : ");
                        String registerEmail = scanner.nextLine();
                        System.out.print("Entrez le mot de passe : ");
                        String registerPassword = scanner.nextLine();

                        // Créez l'utilisateur
                        Member newMember = new Member();
                        newMember.setUser_name(registerUsername);
                        newMember.setEmail(registerEmail);
                        newMember.setPassword(registerPassword);

                        // Enregistrez l'utilisateur
                        memberService.register(newMember);
                        break;

                    case 2:
                        // Connexion
                        System.out.print("Entrez l'email : ");
                        String loginEmail = scanner.nextLine();
                        System.out.print("Entrez le mot de passe : ");
                        String loginPassword = scanner.nextLine();

                        // Appel de la méthode login
                        Optional<User> user = userService.login(loginEmail, loginPassword);

                        if (user.isPresent()) {
                            System.out.println(" Hello : " + user.get().getUser_name());//                          System.out.println("Rôle : " + user.get().getRole());

                            if (user.get().getRole() == Role.ADMIN) { // Vérification du rôle de l'utilisateur
                                // Menu pour les administrateurs
                                System.out.println("Choisissez une option :");
                                System.out.println("1. Gestion des managers");
                                System.out.println("2. Gestion des abonnements");
                                System.out.println("3. Déconnecter");
                                int adminChoice = scanner.nextInt();
                                scanner.nextLine(); // Nettoyer le scanner

                                switch (adminChoice) {
                                    case 1:
                                        // Gestion des managers
                                        GestionManager gestionManager = new GestionManager(connection);
                                        gestionManager.displayManagerMenu(); // Méthode qui affiche le menu de gestion des managers
                                        break;

                                    case 2:
                                        // Gestion des abonnements
                                        System.out.println("Gestion des abonnements en cours...");
                                        // Ici, vous pouvez implémenter la gestion des abonnements
                                        break;

                                    case 3:
                                        return;
                                    default:
                                        System.out.println("Choix invalide.");
                                        break;
                                }
                            } else {
                                System.out.println("Vous n'êtes pas autorisé à accéder à ces options.");
                            }
                        } else {
                            System.out.println("Email ou mot de passe invalide.");
                        }

                        break;

                    case 3:
                        // Réinitialisation du mot de passe
                        System.out.print("Entrez l'email pour réinitialiser le mot de passe : ");
                        String resetEmail = scanner.nextLine();

                        //resetPwd methode
                        userService.resetPwd(resetEmail,SendMail.Mail(resetEmail));
                        System.out.println("Veuillez consulter votre boîte email pour recevoir votre nouveau mot de passe.\n");
                        break;

                    case 4:
                        System.out.println("Au revoir !");
                        return;

                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            scanner.close();
        }
    }
}
