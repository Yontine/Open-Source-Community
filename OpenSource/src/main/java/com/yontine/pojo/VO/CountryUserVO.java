package com.yontine.pojo.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryUserVO {
    private String location;
    private int users;
    private int rank;
}
