package com.sparta.miniprojectbe.repository;

import com.sparta.miniprojectbe.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
