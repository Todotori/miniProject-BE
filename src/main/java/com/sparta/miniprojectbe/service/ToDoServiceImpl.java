package com.sparta.miniprojectbe.service;

import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoCreateRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoUpdateDoneRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoUpdateRequestDto;
import com.sparta.miniprojectbe.domain.dto.response.ToDoResponseDto;
import com.sparta.miniprojectbe.domain.entity.Member;
import com.sparta.miniprojectbe.domain.entity.ToDo;
import com.sparta.miniprojectbe.domain.enums.ErrorCode;
import com.sparta.miniprojectbe.exception.CustomException;
import com.sparta.miniprojectbe.repository.MemberRepository;
import com.sparta.miniprojectbe.repository.ToDoRepository;
import com.sparta.miniprojectbe.security.UserDetailsImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ToDoServiceImpl implements ToDoService {

  private final ToDoRepository toDoRepository;
  private final MemberRepository memberRepository;


  // create
  @Override
  @Transactional
  public ToDoResponseDto create(ToDoCreateRequestDto toDoCreateRequestDto) {

    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Member member = userDetails.getMember();


    ToDo toDo = new ToDo(toDoCreateRequestDto, member);

    toDo = toDoRepository.save(toDo);

    return new ToDoResponseDto(toDo);
  }

  // update
  @Override
  @Transactional
  public ToDoResponseDto update(Long id, ToDoUpdateRequestDto toDoUpdateRequestDto) {
    ToDo toDo = toDoRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(toDo.getMember().getId(), currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }

    toDo.update(toDoUpdateRequestDto);

    toDo = toDoRepository.save(toDo);
    System.out.println(toDo);
    return new ToDoResponseDto(toDo);
  }

  //완료 체크
  @Override
  @Transactional
  public ToDoResponseDto updateDone(Long id, ToDoUpdateDoneRequestDto toDoUpdateDoneRequestDto) {
    ToDo toDo = toDoRepository.findById(toDoUpdateDoneRequestDto.getId()).orElseThrow(EntityNotFoundException::new);

    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(toDo.getMember().getId(), currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }

    toDo.updateDone(toDoUpdateDoneRequestDto);

    toDo = toDoRepository.save(toDo);

    return new ToDoResponseDto(toDo);
  }


  //전체 조회  (로그인 안한 사람)
  @Override
  public List<ToDoResponseDto> getALlList() {

    List<ToDo> toDoList = toDoRepository.findAllByOrderByModifiedAtDesc();

    List<ToDoResponseDto> toDoResponseDtoList = new ArrayList<>();
    for (ToDo toDo : toDoList) {
      toDoResponseDtoList.add(new ToDoResponseDto(toDo));
    }

    return toDoResponseDtoList;
  }



  // select - 1개 조회 (로그인 사용자)
  @Override
  public ToDoResponseDto get(Long id) { // ToDo_id

    ToDo toDo = toDoRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(toDo.getMember().getId(), currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }

    return new ToDoResponseDto(toDo);
  }

  // select List - 전체 조회 (로그인 사용자)
  @Override
  public List<ToDoResponseDto> getList(Long id) {//user_id

    Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(id, currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }

    List<ToDo> toDoList = toDoRepository.findByMember(member);

    List<ToDoResponseDto> toDoResponseDtoList = new ArrayList<>();
    for (ToDo toDo : toDoList) {
      toDoResponseDtoList.add(new ToDoResponseDto(toDo));
    }

    return toDoResponseDtoList;
  }

  //ToDoDone만 조회 (완료만 조회) (로그인 사용자)
  @Override
  public List<ToDoResponseDto> getDoneList(Long id){//user_id

    Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(id, currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }

    List<ToDo> toDoList = toDoRepository.findByMemberAndDone(member,true);

    List<ToDoResponseDto> toDoResponseDtoList = new ArrayList<>();
    for (ToDo toDo : toDoList){
      toDoResponseDtoList.add(new ToDoResponseDto(toDo));
    }

    return toDoResponseDtoList;
  }

  //ToDoDone 안된 것 조회 (미완료만 조회) (로그인 사용자)
  @Override
  public List<ToDoResponseDto> getUndoneList(Long id){//user_id

    Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(id, currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }

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
  public void delete(Long id) { // ToDo_id
    ToDo toDo = toDoRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(toDo.getMember().getId(), currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }

    toDoRepository.delete(toDo);
  }
}
