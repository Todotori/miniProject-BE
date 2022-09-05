package com.sparta.miniprojectbe.service;

import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoCreateRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoUpdateDoneRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoUpdateRequestDto;
import com.sparta.miniprojectbe.domain.dto.response.ToDoResponseDto;
import java.util.List;
import java.util.Set;

public interface ToDoService {

  // create
  ToDoResponseDto create(ToDoCreateRequestDto toDoCreateRequestDto);

  // update
  ToDoResponseDto update(Long id, ToDoUpdateRequestDto toDoUpdateRequestDto);

  ToDoResponseDto updateDone(Long id, ToDoUpdateDoneRequestDto toDoUpdateDoneRequestDto);

  // select - 1개 조회
  ToDoResponseDto get(Long id);

  // select List - 전체 조회
  List<ToDoResponseDto> getList();

  //ToDoDone만 조회 (완료된 것만 조회)
  List<ToDoResponseDto> getDoneList();

  //ToDoDone 안된 것 조회 (미완료된 것만 조회)
  List<ToDoResponseDto> getUndoneList();

  // delete
  void delete(Long id);

//  // tag 체크 목록 조회
//  Set<String> getByTagNameList();
//
//  // tag 조회
//  // tag 조회 시 => String으로 "tag1,tag2,tag3" 이렇게 값이 들어 와야한다.
//  List<ToDoResponseDto> getByTag(String tag);
}
