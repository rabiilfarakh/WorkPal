package org.example.repository.inter;

import org.example.entity.Manager;
import org.example.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    void register(Member member);

    // Read
    Optional<Member> findById(Integer id);
    Optional<Member> findByEmail(String email);
    List<Member> findAll();

    // Update
    void update(Member member);

    // Delete
    void deleteById(Integer id);
}
