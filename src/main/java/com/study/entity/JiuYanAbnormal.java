package com.study.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("jiuyan_abnormal")
public class JiuYanAbnormal {
    private Integer abnormalId;
    private String code;
    private String name;
    /**  异动原因分类 */
    private String abnormalName;
    /**  异动原因分类描述 */
    private String abnormalReason;
    /** 最新价  */
    private Integer price;
    /** 涨幅 */
    private Integer sharesRange;
    /** 涨停时间 */
    private String limitTime;
    /** 股票涨停解析 */
    private String articleExpound;

    private String tradeDate;
    /** 几天几板 */
    private String LimitNum;

    private Integer createTime;

}
