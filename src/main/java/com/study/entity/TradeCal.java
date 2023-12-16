package com.study.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("trade_cal")
public class TradeCal {
    private Integer id;
    private String exchange;
    private String calDate;
    private Boolean isOpen;
    @TableField(value = "pretrade_date")
    private String preTradeDate;
}
