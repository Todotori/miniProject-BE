package com.sparta.miniprojectbe.repository;

import com.sparta.miniprojectbe.domain.entity.Member;
import com.sparta.miniprojectbe.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByMember(Member member);
}
