package com.sparta.miniprojectbe.domain.dto.response;

import com.sparta.miniprojectbe.domain.entity.ToDo;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ToDoResponseDto {

  private Long id;

  private String title;

  private String content;

  private String tag;

  private boolean done;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  private MemberResponseDto member;

  public ToDoResponseDto(ToDo toDo) {
    this.id = toDo.getId();
    this.title = toDo.getTitle();
    this.content = toDo.getContent();
    this.tag = toDo.getTag();
    this.done = toDo.isDone();
    this.createdAt = toDo.getCreatedAt();
    this.modifiedAt = toDo.getModifiedAt();
    this.member = new MemberResponseDto(toDo.getMember());
  }
}
