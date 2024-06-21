package com.yontine.pojo.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVO {
    private String name;
    private String url;
    private String description;
    private String language;
    private int star;
    private int rank;
}
