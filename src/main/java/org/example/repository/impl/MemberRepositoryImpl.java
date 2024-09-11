package org.example.repository.impl;

import org.example.entity.Member;
import org.example.repository.inter.MemberRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberRepositoryImpl implements MemberRepository {

    private Connection connection;

    public MemberRepositoryImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void register(Member member) {
        String sql = "INSERT INTO members (user_name, email, password, role) " +
                "VALUES (?, ?, ?, ?::role_enum) ";

        // Hash the password using BCrypt
        String hashedPassword = BCrypt.hashpw(member.getPassword(), BCrypt.gensalt());

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getUser_name());
            statement.setString(2, member.getEmail());
            statement.setString(3, hashedPassword);
            statement.setString(4, "MEMBER");

            statement.executeUpdate();
            System.out.println("Member registered successfully");

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Member> findById(Integer id) {
        String sql = "SELECT * FROM members WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Directly map the ResultSet to a Member object
                Member member = new Member();
                member.setUser_id(resultSet.getInt("user_id"));
                member.setUser_name(resultSet.getString("user_name"));
                member.setEmail(resultSet.getString("email"));

                return Optional.of(member);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Directly map the ResultSet to a Member object
                Member member = new Member();
                member.setUser_id(resultSet.getInt("user_id"));
                member.setUser_name(resultSet.getString("user_name"));
                member.setEmail(resultSet.getString("email"));

                return Optional.of(member);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Member member = new Member();
                member.setUser_id(resultSet.getInt("user_id"));
                member.setUser_name(resultSet.getString("user_name"));
                member.setEmail(resultSet.getString("email"));
                // add more colomn
                members.add(member);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return members;
    }

    @Override
    public void update(Member member) {

        String sql = "UPDATE members SET user_name = ?, email = ? WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,member.getUser_name());
            statement.setString(2,member.getEmail());
            statement.setInt(3,member.getUser_id());

            int result = statement.executeUpdate();
            if (result > 0) {
                System.out.println("Member was successfully Updating");
            } else {
                System.out.println("Updating member failed, no rows affected");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM members WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);

            int result = statement.executeUpdate();
            if (result > 0) {
                System.out.println("Member with ID " + id + " was successfully deleted.");
            } else {
                System.out.println("No member found with ID " + id + ".");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
