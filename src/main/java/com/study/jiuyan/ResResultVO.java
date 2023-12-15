package com.study.jiuyan;

import lombok.Data;

import java.util.List;

@Data
public class ResResultVO {
    private String errorCode;
    private String msg;
    private Integer serverTime;
    private List<TypeInfoVO> data;
}
