package com.sparta.miniprojectbe.domain.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class ToDoRequestDto {

  @Getter
  public static class ToDoCreateRequestDto{

    private String title;

    private String content;

    private String tag;
//TODO: tag 여러개 받을 수 있도록 바꿔주기?
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
