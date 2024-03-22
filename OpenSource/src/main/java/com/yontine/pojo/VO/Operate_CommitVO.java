package com.yontine.pojo.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operate_CommitVO {
    private int operate;
    private int commit;
    private LocalDate time;
}
