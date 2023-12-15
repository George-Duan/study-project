package com.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResultDTO {
    //@Schema(description = "是否成功")
    private Boolean success;
    //@Schema(description = "处理时间")
    private String systemTime;
    //@Schema(description = "接口返回内容")
    private Object data;
    //@Schema(description = "错误码")
    private String code;
    //@Schema(description = "错误信息")
    private String message;
}