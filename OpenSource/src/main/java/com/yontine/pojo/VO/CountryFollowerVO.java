package com.yontine.pojo.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryFollowerVO {
    private String location;
    private int followers;
    private int rank;
}
