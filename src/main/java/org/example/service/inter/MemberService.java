package org.example.service.inter;

import org.example.entity.Member;
import org.example.entity.User;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    void register(Member member);

    Optional<Member> findById(Integer id);
    Optional<Member> findByEmail(String email);
    List<Member> findAll();

    // Update
    void update(Member member);

    // Delete
    void deleteById(Integer id);
}
