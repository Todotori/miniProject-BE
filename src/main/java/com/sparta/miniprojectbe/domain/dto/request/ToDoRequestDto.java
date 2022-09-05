package com.sparta.miniprojectbe.domain.dto.request;

import lombok.Getter;

@Getter
public class ToDoRequestDto {

  @Getter
  public static class ToDoCreateRequestDto{

    private String title;

    private String content;

    private String tag;
  }

  @Getter
  public static class ToDoUpdateRequestDto{

    private String title;

    private String content;

    private String tag;

    private boolean done;
  }


  @Getter
  public static class ToDoUpdateDoneRequestDto{

    private Long id;

    private boolean done;
  }

}
