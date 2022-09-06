package com.sparta.miniprojectbe.domain.entity;

import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoCreateRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoUpdateDoneRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.ToDoRequestDto.ToDoUpdateRequestDto;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ToDo extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String content;

  private String tag;

  private boolean done;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  public ToDo(ToDoCreateRequestDto toDoCreateRequestDto, Member member) {
    this.title = toDoCreateRequestDto.getTitle();
    this.content = toDoCreateRequestDto.getContent();
    this.tag = toDoCreateRequestDto.getTag();
    this.done = false;
    this.member = member;
  }
  // done : false 미완료 / true 완료

  public void update(ToDoUpdateRequestDto toDoUpdateRequestDto) {
    this.title = toDoUpdateRequestDto.getTitle();
    this.content = toDoUpdateRequestDto.getContent();
    this.tag = toDoUpdateRequestDto.getTag();
    this.done = toDoUpdateRequestDto.isDone();
  }

  public void updateDone(ToDoUpdateDoneRequestDto toDoUpdateDoneRequestDto) {
    this.done = toDoUpdateDoneRequestDto.isDone();
  }
}
