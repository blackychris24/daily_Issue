package com.example.daily_issue.chatting.security.service;

import com.example.daily_issue.chatting.dao.repository.member.MemberRepository;
import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import com.example.daily_issue.chatting.security.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ChatMemberEntity user = Optional
                .ofNullable(memberRepository.findByUserId(username))
                .orElseThrow(() -> new UsernameNotFoundException(username))
                ;

        LoginUser loginUser = new LoginUser(
                user.getUserId()
                , passwordEncoder.encode(user.getUserPw())
                , user.getUserName()
                , user.getUserNickName()
        );

        return loginUser;
    }


    public Authentication getAuthentication()
    {
        Authentication authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(a -> a instanceof UsernamePasswordAuthenticationToken)
                .orElseThrow(() -> new RuntimeException("Not Logged or Authentication is not UsernamePasswordAuthenticationToken ClassType"));

        return authentication;
    }

    public LoginUser getPrincipal()
    {
        LoginUser principal = (LoginUser) Optional.ofNullable(getAuthentication().getPrincipal())
                .filter(p -> p instanceof LoginUser)
                .orElseThrow(() -> new RuntimeException("Not Logged or Principal is not LoginUser ClassType"));

        return principal;
    }

    public void updatePrincipal(@NotNull LoginUser loginUser) {
        Authentication authentication = getAuthentication();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginUser
                , authentication.getCredentials()
                , authentication.getAuthorities());
        token.setDetails(authentication.getDetails());

        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
