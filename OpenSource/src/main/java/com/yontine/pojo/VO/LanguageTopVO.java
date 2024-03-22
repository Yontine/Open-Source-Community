package com.yontine.pojo.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageTopVO {
    private String language;
    private int project_num;
    private int users;
    private int rank;
}
