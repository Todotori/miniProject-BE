package com.sparta.miniprojectbe.service;

import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoCreateRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoUpdateDoneRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoUpdateRequestDto;
import com.sparta.miniprojectbe.domain.dto.response.ToDoResponseDto;
import com.sparta.miniprojectbe.domain.entity.Member;
import com.sparta.miniprojectbe.domain.entity.ToDo;
import com.sparta.miniprojectbe.repository.MemberRepository;
import com.sparta.miniprojectbe.repository.ToDoRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ToDoServiceImpl implements ToDoService {

  private final ToDoRepository toDoRepository;
  private final MemberRepository memberRepository;


  //테스트용 멤버 1로 강제 주입했으므로 멤버 가지고 오는거로 바꿔줘야 함!

  // create
  @Override
  @Transactional
  public ToDoResponseDto create(ToDoCreateRequestDto toDoCreateRequestDto) {
    // TODO: 나중에 다른 팀원이 회원, securiy 개발 후 merge 시 변경
//    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Member member = memberRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

    ToDo toDo = new ToDo(toDoCreateRequestDto, member);

    toDo = toDoRepository.save(toDo);

    return new ToDoResponseDto(toDo);
  }

  // update
  @Override
  @Transactional
  public ToDoResponseDto update(Long id, ToDoUpdateRequestDto toDoUpdateRequestDto) {
    // TODO: 나중에 다른 팀원이 회원, securiy 개발 후 merge 시 변경 및 동일 사용자 비교 로직 추가
//    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    ToDo toDo = toDoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    toDo.update(toDoUpdateRequestDto);

    toDo = toDoRepository.save(toDo);

    return new ToDoResponseDto(toDo);
  }

  @Override
  @Transactional
  public ToDoResponseDto updateDone(Long id, ToDoUpdateDoneRequestDto toDoUpdateDoneRequestDto) {
    // TODO: 나중에 다른 팀원이 회원, securiy 개발 후 merge 시 변경 및 동일 사용자 비교 로직 추가
//    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//    ToDo toDo = toDoRepository.findById(toDoUpdateDoneRequestDto.getId()).orElseThrow(EntityNotFoundException::new);
    ToDo toDo = toDoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    toDo.updateDone(toDoUpdateDoneRequestDto);

    toDo = toDoRepository.save(toDo);

    return new ToDoResponseDto(toDo);
  }

  // select - 1개 조회
  @Override
  public ToDoResponseDto get(Long id) { // ToDo_id
    // TODO: 나중에 다른 팀원이 회원, securiy 개발 후 merge 시 변경 및 동일 사용자 비교 로직 추가
//    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    ToDo toDo = toDoRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    return new ToDoResponseDto(toDo);
  }

  // select List - 전체 조회
  // 체크 리스트에서 all
  @Override
  public List<ToDoResponseDto> getList() {

    // TODO: 나중에 다른 팀원이 회원, securiy 개발 후 merge 시 변경
//    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Member member = memberRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

    List<ToDo> toDoList = toDoRepository.findByMember(member);

    List<ToDoResponseDto> toDoResponseDtoList = new ArrayList<>();
    for (ToDo toDo : toDoList) {
      toDoResponseDtoList.add(new ToDoResponseDto(toDo));
    }

    return toDoResponseDtoList;
  }

  //ToDoDone만 조회 (완료된 것만 조회)
  @Override
  public List<ToDoResponseDto> getDoneList(){

    // TODO: 나중에 다른 팀원이 회원, securiy 개발 후 merge 시 변경
//    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Member member = memberRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

    List<ToDo> toDoList = toDoRepository.findByMemberAndDone(member,true);

    List<ToDoResponseDto> toDoResponseDtoList = new ArrayList<>();
    for (ToDo toDo : toDoList){
      toDoResponseDtoList.add(new ToDoResponseDto(toDo));
    }

    return toDoResponseDtoList;
  }

  //ToDoDone 안된 것 조회 (미완료된 것만 조회)
  @Override
  public List<ToDoResponseDto> getUndoneList(){

    // TODO: 나중에 다른 팀원이 회원, securiy 개발 후 merge 시 변경
//    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Member member = memberRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

    List<ToDo> toDoList = toDoRepository.findByMemberAndDone(member,false);

    List<ToDoResponseDto> toDoResponseDtoList = new ArrayList<>();
    for (ToDo toDo : toDoList){
      toDoResponseDtoList.add(new ToDoResponseDto(toDo));
    }

    return toDoResponseDtoList;
  }


  // delete
  @Override
  @Transactional
  public void delete(Long id) { // ToDo id
    // TODO: 나중에 다른 팀원이 회원, securiy 개발 후 merge 시 변경 및 동일 사용자 비교 로직 추가
//    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    ToDo toDo = toDoRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    toDoRepository.delete(toDo);
  }


//  // tag 조회
//  // tag 조회 시 => String으로 "tag1,tag2,tag3" 이렇게 값이 들어 와야한다.
//  @Override
//  public List<ToDoResponseDto> getByTag(String tag) {
//    // TODO: 나중에 다른 팀원이 회원, securiy 개발 후 merge 시 변경 및 동일 사용자 비교 로직 추가
////    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    Member member = memberRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
//
//    String[] tags = tag.split(","); // String으로 받아 온 ',' 중심을 자름
//
//    Set<ToDo> toDoSet = new HashSet<>();
//    for (String currentTag : tags) {
//      List<ToDo> toDoList = toDoRepository.findByMemberAndTagContains(member,
//          currentTag); // 콤마(,)로 나눈 tag들을 하나씩 조회해서 가져온다.
//      // Set에 저장해서 중복을 없앤다.
//      //        toDoSet.add(toDo); 을 인텔리제이가 바꿔준 것
//      toDoSet.addAll(toDoList);
//    }
//
//    // Response DTO에 데이터를 넣기 위한 for문
//    List<ToDoResponseDto> toDoResponseDtoList = new ArrayList<>();
//    for (ToDo toDo : toDoSet) {
//      toDoResponseDtoList.add(new ToDoResponseDto(toDo));
//    }
//
//    return toDoResponseDtoList;
//  }
//
//  // TODO : 회의 후 쓸지 뺄지 정한다.
//
//  // tag 체크 목록 조회 (비즈니스 로직으로 목록 만들기)
//  //tag를 사용자가 직접 적어서 저장하는 경우
//  // tag가 이미 지정되는 경우 (카테고리)에는 따로 List불러오는 건 프론트에서!
//  @Override
//  public Set<String> getByTagNameList() {
//    // TODO: 나중에 다른 팀원이 회원, securiy 개발 후 merge 시 변경 및 동일 사용자 비교 로직 추가
////    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    Member member = memberRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
//
//    List<ToDo> toDoList = toDoRepository.findByMember(member);
//
//    // Tag의 Name이 중복 되지 않게 전달하기 위해 Set을 사용
//    Set<String> tagNameList = new HashSet<>();
//    for (ToDo toDo : toDoList) {
//      // ToDo의 tag가 tag1,tag2,tag3으로 들어가므로 콤마를 기점으로 나눠준다.
//      String[] tags = toDo.getTag().split(",");
//
//      // 아래의 for문을 간략히 바꾸면 Arrays.asList를 사용하면 된다. Collection에 배열을 더할 때 사용한다.
//      tagNameList.addAll(Arrays.asList(tags));
//
////      for(String tag : tags){
////        tagNameList.add(tag);
////      }
//    }
//
//    return tagNameList;
//  }

}
