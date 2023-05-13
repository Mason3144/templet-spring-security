package com.templete.member;

import com.templete.auth.utils.HelloAuthorityUtils;
import com.templete.exception.BusinessLogicException;
import com.templete.exception.ExceptionCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
//@Service
public class DBMemberService implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final HelloAuthorityUtils authorityUtils;

    public DBMemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, HelloAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
        System.out.println("# Create Member in DB");
        return memberRepository.save(member);
    }

    private void verifyExistsEmail(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(optionalMember.isPresent()) throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }
}
