package org.example.entity;

import org.example.enumeration.Role;

public class Manager extends User {

    private String phone;

    public Manager(Integer user_id, String user_name, String email, String password, Role role, String phone) {
        super(user_id, user_name, email, password, role);
        this.phone = phone;
    }

    public Manager() {
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
