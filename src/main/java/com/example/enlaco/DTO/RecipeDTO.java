package com.example.enlaco.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDTO {
    private Integer rid;
    private String  rimg;
    private String  rmenu;
    private String  rcontent;
    private String  rwriter;
    private String  rclass;
    private String  rselect;
    private Integer rviewcnt;
    private Integer rgoodcnt;

    private Integer         mid;
}
