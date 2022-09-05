package com.sparta.miniprojectbe.jwt;

import com.sparta.miniprojectbe.domain.entity.Member;
import com.sparta.miniprojectbe.repository.MemberRepository;
import com.sparta.miniprojectbe.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
  private final MemberRepository memberRepository;

//  @Override
//  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//    Optional<Member> member = memberRepository.findByEmail(email);
//    return member
//        .map(UserDetailsImpl::new)
//        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
//  }

  @Override
  public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
    Optional<Member> member = memberRepository.findByNickname(nickname);
    return member
        .map(UserDetailsImpl::new)
        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
  }
}
