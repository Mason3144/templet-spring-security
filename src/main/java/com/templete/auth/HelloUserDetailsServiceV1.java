package com.templete.auth;

import com.templete.auth.utils.HelloAuthorityUtils;
import com.templete.member.Member;
import com.templete.member.MemberRepository;
import com.templete.exception.BusinessLogicException;
import com.templete.exception.ExceptionCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class HelloUserDetailsServiceV1 implements UserDetailsService { // (1)
    private final MemberRepository memberRepository;
    private final HelloAuthorityUtils authorityUtils;

    // (2)
    public HelloUserDetailsServiceV1(MemberRepository memberRepository, HelloAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

    // (3)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(username); // (3-1)
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)); // (3-2)

        return new HelloUserDetails(findMember);
    }

    // (4)
    private final class HelloUserDetails extends Member implements UserDetails { // (4-1)
        // (4-2)
        HelloUserDetails(Member member) {
            setMemberId(member.getMemberId());
            setFullName(member.getFullName());
            setEmail(member.getEmail());
            setPassword(member.getPassword());
            setRoles(member.getRoles());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());  // (4-3)
        }

        // (4-3)
        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }
        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}