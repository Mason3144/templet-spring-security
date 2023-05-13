package com.templete.config;

import com.templete.auth.utils.HelloAuthorityUtils;
import com.templete.member.MemberRepository;
import com.templete.member.MemberService;
import com.templete.member.DBMemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class JavaConfiguration {
    @Bean
    public MemberService dbMemberService(MemberRepository memberRepository,
                                         PasswordEncoder passwordEncoder,
                                         HelloAuthorityUtils authorityUtils
    ) {
        return new DBMemberService(memberRepository, passwordEncoder, authorityUtils);
    }
//    @Bean
//    public MemberService inMemoryMemberService(UserDetailsManager userDetailsManager,
//                                               PasswordEncoder passwordEncoder) {
//        return new InMemoryMemberService(userDetailsManager, passwordEncoder);
//    }
}
