package org.example.service.impl;

import org.example.entity.Member;
import org.example.entity.User;
import org.example.repository.inter.MemberRepository;
import org.example.service.inter.MemberService;

import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void register(Member member) {
        memberRepository.register(member);
    }

    @Override
    public Optional<Member> findById(Integer id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public void update(Member member) {
        memberRepository.update(member);
    }

    @Override
    public void deleteById(Integer id) {
        memberRepository.deleteById(id);
    }
}


