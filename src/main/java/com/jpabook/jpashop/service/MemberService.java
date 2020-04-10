package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findByOne(Long id){
        return memberRepository.findById(id).orElseGet(() -> null);
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Long join(Member member){
        return memberRepository.save(member).getId();
    }

    @Transactional
    public Member update(Long id, String name){
        Member member = memberRepository.findById(id).orElseGet(() -> null);
        member.setName(name);
        return member;
    }
}
