package com.yontine.pojo.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageVO {
    private String C;
    private String C_Plus;
    private String Java;
    private String JavaScript;
    private String Python;
    private String PHP;
    private String TypeScript;
    private String Rust;
    private String kotlin;
    private LocalDate time;
}
