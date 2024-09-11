package org.example.service.inter;

import org.example.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> login(String email, String paswword);

    void resetPwd(String email,String password);
}
