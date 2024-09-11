package org.example.service.impl;

import org.example.entity.User;
import org.example.repository.inter.UserRepository;
import org.example.service.inter.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> login(String email, String paswword) {
        return userRepository.login(email,paswword);
    }

    @Override
    public void resetPwd(String email,String paswword) {
        userRepository.resetPwd(email, paswword);
    }
}
