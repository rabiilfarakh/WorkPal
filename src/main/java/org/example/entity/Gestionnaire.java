package org.example.entity;

import org.example.enumeration.Role;

public class Gestionnaire extends User{

    public Gestionnaire(Integer user_id, String user_name, String email, String password, Role role) {
        super(user_id, user_name, email, password, role);
    }

    public Gestionnaire() {
    }
}
