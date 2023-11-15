//2023-11-15 정선아 : rRegDate, rModDate 추가
package com.example.enlaco.DTO;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDTO {
    private Integer rid;
    private String  rimg;
    @NotBlank(message = "메뉴명을 입력하세요")
    private String  rmenu;
    private String  rcontent;
    private String  rwriter;
    private String  rclass;
    private String  rtime;
    private String  rselect;
    private Integer rviewcnt;
    private Integer rgoodcnt;
    private LocalDateTime rRegDate;
    private LocalDateTime rModDate;

    private Integer         mid;
}
