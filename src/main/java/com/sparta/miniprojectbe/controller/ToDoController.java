package com.sparta.miniprojectbe.controller;

import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoCreateRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoUpdateDoneRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoUpdateRequestDto;
import com.sparta.miniprojectbe.domain.dto.response.ResponseDto;
import com.sparta.miniprojectbe.domain.dto.response.ToDoResponseDto;
import com.sparta.miniprojectbe.domain.enums.ErrorCode;
import com.sparta.miniprojectbe.security.UserDetailsImpl;
import com.sparta.miniprojectbe.service.ToDoService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ToDoController {
  private final ToDoService toDoService;

  //전체조회 (미로그인)
  @GetMapping("/api/todo/all")
  public ResponseDto<List<ToDoResponseDto>> getAll(){
    List<ToDoResponseDto> toDoResponseDtoList;

    try {
      toDoResponseDtoList = toDoService.getALlList();
    }catch (EntityNotFoundException e){
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.ENTITY_NOT_FOUND);
    }catch (Exception e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.INVALID_ERROR);
    }

    return new ResponseDto<>(toDoResponseDtoList);
  }



  // 1개 조회
  @GetMapping("/api/todo/{id}")//todo_id
  public ResponseDto<ToDoResponseDto> get(@PathVariable Long id){
    ToDoResponseDto toDoResponseDto;
    try {
      toDoResponseDto = toDoService.get(id);
    }catch (EntityNotFoundException e){
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.ENTITY_NOT_FOUND);
    }catch (Exception e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.INVALID_ERROR);
    }

    return new ResponseDto<>(toDoResponseDto);
  }

  // 전체 조회 (로그인)
  @GetMapping("/api/todo")
  public ResponseDto<List<ToDoResponseDto>> getList(@AuthenticationPrincipal UserDetailsImpl userDetails){
    List<ToDoResponseDto> toDoResponseDtoList;
    // 로그인 되어 있는 회원 테이블의 ID
    Long memberId = userDetails.getMember().getId();

    try {
      toDoResponseDtoList = toDoService.getList(memberId);
    }catch (EntityNotFoundException e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.ENTITY_NOT_FOUND);
    }catch (Exception e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(toDoResponseDtoList);
  }

  //완료 조회(로그인)
  @GetMapping("/api/todo/done")
  public ResponseDto<List<ToDoResponseDto>> getDoneList(@AuthenticationPrincipal UserDetailsImpl userDetails){
    List<ToDoResponseDto> toDoResponseDtoList;
    // 로그인 되어 있는 회원 테이블의 ID
    Long memberId = userDetails.getMember().getId();
    try {
      toDoResponseDtoList = toDoService.getDoneList(memberId);
    }catch (EntityNotFoundException e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.ENTITY_NOT_FOUND);
    }catch (Exception e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(toDoResponseDtoList);
  }

  //미완료 조회(로그인)
  @GetMapping("/api/todo/undone")
  public ResponseDto<List<ToDoResponseDto>> getUnDoneList(@AuthenticationPrincipal UserDetailsImpl userDetails){
    List<ToDoResponseDto> toDoResponseDtoList;
    // 로그인 되어 있는 회원 테이블의 ID
    Long memberId = userDetails.getMember().getId();
    try {
      toDoResponseDtoList = toDoService.getUndoneList(memberId);
    }catch (EntityNotFoundException e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.ENTITY_NOT_FOUND);
    }catch (Exception e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(toDoResponseDtoList);
  }


  // create
  @PostMapping("/api/todo")
  public ResponseDto<ToDoResponseDto> create(@RequestBody ToDoCreateRequestDto toDoCreateRequestDto){
    ToDoResponseDto toDoResponseDto;
    try {
      toDoResponseDto = toDoService.create(toDoCreateRequestDto);
    }catch (EntityNotFoundException e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.ENTITY_NOT_FOUND);
    }catch (Exception e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(toDoResponseDto);
  }

  // update
  @PutMapping("/api/todo/{id}")
  public ResponseDto<ToDoResponseDto> update(@PathVariable Long id, @RequestBody ToDoUpdateRequestDto toDoUpdateRequestDto){
    ToDoResponseDto toDoResponseDto;
    try {
      toDoResponseDto = toDoService.update(id, toDoUpdateRequestDto);
    }catch (EntityNotFoundException e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.ENTITY_NOT_FOUND);
    }catch (Exception e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(toDoResponseDto);
  }

  // update done
  @PutMapping("/api/todo/{id}/done")
  public ResponseDto<ToDoResponseDto> updateDone(@PathVariable Long id, @RequestBody ToDoUpdateDoneRequestDto toDoUpdateDoneRequestDto){
    ToDoResponseDto toDoResponseDto;
    try{
      toDoResponseDto = toDoService.updateDone(id, toDoUpdateDoneRequestDto);
    }catch (EntityNotFoundException e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.ENTITY_NOT_FOUND);
    }catch (Exception e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.INVALID_ERROR);
    }

    return new ResponseDto<>(toDoResponseDto);
  }

  // delete
  @DeleteMapping("/api/todo/{id}")
  public ResponseDto<String> delete(@PathVariable Long id){
    try {
      toDoService.delete(id);
    }catch (EntityNotFoundException e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.ENTITY_NOT_FOUND);
    }catch (Exception e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>("delete success");
  }

//  // tag별 조회
//  @GetMapping("/api/todo/tag")
//  public ResponseDto<List<ToDoResponseDto>> getByTag(@RequestParam(name = "tag") String tag){
//    List<ToDoResponseDto> toDoResponseDtoList = toDoService.getByTag(tag);
//
//    return new ResponseDto<>(toDoResponseDtoList);
//  }
//
//  // tag Name List 가져오기
//  @GetMapping("/api/todo/tagList")
//  public ResponseDto<Set<String>> getByTagNameList(){
//    Set<String> tagNameSet = toDoService.getByTagNameList();
//
//    return new ResponseDto<>(tagNameSet);
//  }

}
