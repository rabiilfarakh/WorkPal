package org.example.util;

import org.example.config.Database;
import org.example.enumeration.Role;
import org.example.entity.User;
import org.example.repository.impl.MemberRepositoryImpl;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.repository.inter.MemberRepository;
import org.example.repository.inter.UserRepository;
import org.example.service.impl.MemberServiceImpl;
import org.example.service.impl.UserServiceImpl;
import org.example.service.inter.MemberService;
import org.example.service.inter.UserService;

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
                        System.out.print("Entrez phone number : ");
                        String registerPhone = scanner.nextLine();
                        System.out.print("Entrez l'email : ");
                        String registerEmail = scanner.nextLine();
                        System.out.print("Entrez le mot de passe : ");
                        String registerPassword = scanner.nextLine();

                        // Créez l'utilisateur
                        User newUser = new User();
                        newUser.setUser_name(registerUsername);
                        newUser.setEmail(registerEmail);
                        newUser.setPassword(registerPassword);
                        newUser.setRole(Role.MEMBER);

                        // Enregistrez l'utilisateur
                        memberService.register(newUser);
                        System.out.println("Inscription réussie !");
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
                            System.out.println("Utilisateur trouvé : " + user.get().getUser_name());
                            System.out.println("Rôle : " + user.get().getRole());
                        } else {
                            System.out.println("Email ou mot de passe invalide.");
                        }
                        break;

                    case 3:
                        // Réinitialisation du mot de passe
                        System.out.print("Entrez l'email pour réinitialiser le mot de passe : ");
                        String resetEmail = scanner.nextLine();

                        // userService.resetPassword(resetEmail);

                        System.out.println("Instructions de réinitialisation envoyées !");
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
