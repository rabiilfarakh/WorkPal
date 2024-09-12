    package org.example.view;

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

    public class Auth {

        public static void handleUserOperations() {
            Connection connection = null;
            Scanner scanner = new Scanner(System.in);

            try {
                // Connexion à la base de données
                Database db = Database.getInstance();
                connection = db.getConnection();

                // Initialisation des repositories et services
                UserRepository userRepository = new UserRepositoryImpl(connection);
                UserService userService = new UserServiceImpl(userRepository);

                MemberRepository memberRepository = new MemberRepositoryImpl(connection);
                MemberService memberService = new MemberServiceImpl(memberRepository);

                ManagerRepository managerRepository = new ManagerRepositoryImpl(connection);
                ManagerService managerService = new ManagerServiceImpl(managerRepository);

                while (true) {
                    // Menu d'authentification
                    System.out.println("🔑 Bienvenue ! Que souhaitez-vous faire ?");
                    System.out.println("1️⃣ S'inscrire 📝");
                    System.out.println("2️⃣ Se connecter 🔐");
                    System.out.println("3️⃣ Réinitialiser le mot de passe 🔄");
                    System.out.println("4️⃣ Quitter 🚪");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.print("Entrez votre nom d'utilisateur : ");
                            String registerUsername = scanner.nextLine();
                            System.out.print("Entrez votre email : ");
                            String registerEmail = scanner.nextLine();
                            System.out.print("Entrez votre mot de passe : ");
                            String registerPassword = scanner.nextLine();

                            // Création et enregistrement du membre
                            Member newMember = new Member();
                            newMember.setUser_name(registerUsername);
                            newMember.setEmail(registerEmail);
                            newMember.setPassword(registerPassword);
                            memberService.register(newMember);
                            System.out.println("✅ Inscription réussie !");
                            break;

                        case 2:
                            System.out.print("Entrez votre email : ");
                            String loginEmail = scanner.nextLine();
                            System.out.print("Entrez votre mot de passe : ");
                            String loginPassword = scanner.nextLine();

                            // Tentative de connexion
                            Optional<User> user = userService.login(loginEmail, loginPassword);
                            if (user.isPresent()) {
                                System.out.println("\n👤 Bienvenue, " + user.get().getUser_name().toUpperCase() + " !");
                                Role role = user.get().getRole();

                                if (role == Role.ADMIN) {
                                    System.out.println("🔧 Menu Administrateur :");
                                    adminMenu(scanner, connection);
                                } else if (role == Role.MANAGER) {
                                    System.out.println("🛠️ Menu Manager :");
                                    managerMenu(scanner, connection);
                                } else {
                                    System.out.println("👥 Menu Membre :");
                                    // Ajouter ici le menu membre
                                }
                            } else {
                                System.out.println("❌ Identifiants incorrects !");
                            }
                            break;

                        case 3:
                            System.out.print("Entrez votre email pour réinitialiser le mot de passe : ");
                            String resetEmail = scanner.nextLine();
                            userService.resetPwd(resetEmail, SendMail.Mail(resetEmail));
                            System.out.println("📧 Email de réinitialisation envoyé. Vérifiez votre boîte de réception.");
                            break;

                        case 4:
                            System.out.println("👋 Au revoir !");
                            return;

                        default:
                            System.out.println("⚠️ Choix invalide, réessayez.");
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) connection.close();
                    scanner.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        private static void adminMenu(Scanner scanner, Connection connection) {
            System.out.println("1️⃣ Gestion des managers 👔");
            System.out.println("2️⃣ Gestion des membres 👥");
            System.out.println("3️⃣ Gestion des types d'espace 📅");
            System.out.println("4️⃣ Déconnexion 🔒");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    GestionManager gestionManager = new GestionManager(connection);
                    gestionManager.displayManagerMenu();
                    break;
                case 2:
                    GestionMember gestionMember = new GestionMember(connection);
                    gestionMember.displayMemberMenu();
                    break;
                case 3:
                    GestionSpaceType gestionSpaceType = new GestionSpaceType(connection);
                    gestionSpaceType.displaySpaceTypeMenu();
                    break;
                case 4:
                    System.out.println("🔓 Déconnecté.");
                    break;
                default:
                    System.out.println("⚠️ Choix invalide.");
                    break;
            }
        }

        private static void managerMenu(Scanner scanner, Connection connection) {
            System.out.println("1️⃣ Gestion des espaces 🏢");
            System.out.println("2️⃣ Gestion des membres 👥");
            System.out.println("3️⃣ Gestion des réservations 📅");
            System.out.println("4️⃣ Déconnexion 🔒");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("🛠️ Gestion des espaces...");
                    break;
                case 2:
                    GestionMember gestionMember = new GestionMember(connection);
                    gestionMember.displayMemberMenu();
                    break;
                case 3:
                    System.out.println("📅 Gestion des réservations...");
                    break;
                case 4:
                    System.out.println("🔓 Déconnecté.");
                    break;
                default:
                    System.out.println("⚠️ Choix invalide.");
                    break;
            }
        }
    }
