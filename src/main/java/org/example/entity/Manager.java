package org.example.entity;

import org.example.enumeration.Role;

public class Manager extends User {

    public Manager(Integer user_id, String user_name, String email, String password, Role role) {
        super(user_id, user_name, email, password, role);

    }

    public Manager() {
    }


}
