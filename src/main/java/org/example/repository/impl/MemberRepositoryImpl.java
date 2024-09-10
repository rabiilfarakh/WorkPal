package org.example.repository.impl;

import org.example.entity.Member;
import org.example.repository.inter.MemberRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberRepositoryImpl implements MemberRepository {

    private Connection connection;

    public MemberRepositoryImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void register(Member member) {
        String sql = "INSERT INTO admins (user_name, email, password, role) " +
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
}
