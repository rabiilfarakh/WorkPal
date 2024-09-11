package org.example.util;

import org.example.entity.Member;
import org.example.repository.impl.MemberRepositoryImpl;
import org.example.repository.inter.MemberRepository;
import org.example.service.impl.MemberServiceImpl;
import org.example.service.inter.MemberService;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GestionMember {

    private final MemberService memberService;

    // Constructeur qui initialise le service du member
    public GestionMember(Connection connection) {
        // Créer une instance du repository et du service pour gérer les members
        MemberRepository memberRepository = new MemberRepositoryImpl(connection);
        this.memberService = new MemberServiceImpl(memberRepository);
    }

    public void displayMemberMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choisissez une option :");
            System.out.println("1. Ajouter un member");
            System.out.println("2. Mettre à jour un member");
            System.out.println("3. Supprimer un member");
            System.out.println("4. Afficher tous les members");
            System.out.println("5. Rechercher un member par ID");
            System.out.println("6. Rechercher un member par email");
            System.out.println("7. Quitter");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Entrez le nom d'utilisateur : ");
                    String registerUsername = scanner.nextLine();
                    System.out.print("Entrez l'email : ");
                    String registerEmail = scanner.nextLine();
                    System.out.print("Entrez le mot de passe : ");
                    String registerPassword = scanner.nextLine();

                    // Créez le member
                    Member newMember = new Member();
                    newMember.setUser_name(registerUsername);
                    newMember.setEmail(registerEmail);
                    newMember.setPassword(registerPassword);

                    // Enregistrez le member
                    memberService.register(newMember);
                    System.out.println("Member ajouté avec succès.");
                    break;

                case 2:
                    System.out.print("Entrez l'ID du member à mettre à jour : ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Nettoyer le scanner

                    // Récupérer le member existant
                    Optional<Member> optionalMemberToUpdate = memberService.findById(updateId);
                    if (optionalMemberToUpdate.isPresent()) {
                        Member updateMember = optionalMemberToUpdate.get();
                        System.out.print("Entrez le nouveau nom d'utilisateur : ");
                        String updateUsername = scanner.nextLine();
                        System.out.print("Entrez le nouvel email : ");
                        String updateEmail = scanner.nextLine();
                        System.out.print("Entrez le nouveau mot de passe : ");
                        String updatePassword = scanner.nextLine();

                        // Mettre à jour les informations du member
                        updateMember.setUser_name(updateUsername);
                        updateMember.setEmail(updateEmail);
                        updateMember.setPassword(updatePassword);

                        // Enregistrez les changements
                        memberService.update(updateMember);
                        System.out.println("Member mis à jour avec succès.");
                    } else {
                        System.out.println("Member non trouvé.");
                    }
                    break;

                case 3:
                    System.out.print("Entrez l'ID du member à supprimer : ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine(); // Nettoyer le scanner

                    // Supprimer le member
                    memberService.deleteById(deleteId);
                    break;

                case 4:
                    // Afficher tous les members
                    List<Member> members = memberService.findAll();
                    if (members.isEmpty()) {
                        System.out.println("Aucun member trouvé.");
                    } else {
                        for (Member member : members) {
                            System.out.println("ID: " + member.getUser_id() +
                                    ", Nom d'utilisateur: " + member.getUser_name() +
                                    ", Email: " + member.getEmail());
                        }
                    }
                    break;

                case 5:
                    System.out.print("Entrez l'ID du member à rechercher : ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine();

                    Optional<Member> optionalMemberById = memberService.findById(searchId);
                    if (optionalMemberById.isPresent()) {
                        Member memberById = optionalMemberById.get();
                        System.out.println("ID: " + memberById.getUser_id() +
                                ", Nom d'utilisateur: " + memberById.getUser_name() +
                                ", Email: " + memberById.getEmail());
                    } else {
                        System.out.println("Member avec cet ID non trouvé.");
                    }
                    break;

                case 6:
                    System.out.print("Entrez l'email du member à rechercher : ");
                    String searchEmail = scanner.nextLine();

                    Optional<Member> optionalMemberByEmail = memberService.findByEmail(searchEmail);
                    if (optionalMemberByEmail.isPresent()) {
                        Member memberByEmail = optionalMemberByEmail.get();
                        System.out.println("ID: " + memberByEmail.getUser_id() +
                                ", Nom d'utilisateur: " + memberByEmail.getUser_name() +
                                ", Email: " + memberByEmail.getEmail());
                    } else {
                        System.out.println("Member avec cet email non trouvé.");
                    }
                    break;

                case 7:
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
