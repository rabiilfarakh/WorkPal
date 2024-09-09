package org.example.service.impl;

import org.example.entity.User;
import org.example.repository.inter.MemberRepository;
import org.example.service.inter.MemberService;

public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void register(User user) {
        memberRepository.register(user);
    }
}
