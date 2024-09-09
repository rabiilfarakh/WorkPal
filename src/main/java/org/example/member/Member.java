package org.example.member;

import org.example.user.Role;
import org.example.user.User;

public class Member extends User {
    private Integer memberId;
    private String phone ;

    public Member(Integer userId, String username, String email, String password, Role role, Integer memberId, String phone) {
        super(userId, username, email, password, role);
        this.memberId = memberId;
        this.phone = phone;
    }

    public Member(){

    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
