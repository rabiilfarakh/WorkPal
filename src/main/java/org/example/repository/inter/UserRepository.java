package org.example.repository.inter;

import org.example.entity.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> login(String email, String paswword);
    void resetPwd(String email, String paswword);
}
