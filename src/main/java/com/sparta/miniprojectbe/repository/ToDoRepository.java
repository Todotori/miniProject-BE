package com.sparta.miniprojectbe.repository;

import com.sparta.miniprojectbe.domain.entity.Member;
import com.sparta.miniprojectbe.domain.entity.ToDo;
import java.lang.annotation.Native;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

  List<ToDo> findByMember(Member member);

  List<ToDo> findAllByOrderByModifiedAtDesc();

  // contains는 해당 필드의 값에 검색하려는 값이 포함이 되어있으면 반환해준다.
  List<ToDo> findByMemberAndTagContains(Member member, String tag);

  List<ToDo> findByMemberAndDone(Member member, boolean done);

}
