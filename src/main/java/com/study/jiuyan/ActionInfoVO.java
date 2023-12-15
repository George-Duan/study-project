package com.study.jiuyan;

import lombok.Data;

@Data
public class ActionInfoVO {
    private String action_field_id;
    private String action_info_id;
    private String article_id;
    private String create_time;
    private String day;
    private String delete_time;
    private String edition;
    private String expound;
    private Integer is_crawl;
    private String is_delete;
    private Integer is_recommend;
    private String num;
    //价格，韭研已经做了处理，小数整数化了(*100)
    private Integer price;
    private String reason;
    //涨幅，一样整数化了
    private Integer shares_range;
    private Integer sort_no;
    private String stock_id;
    private String time;
    private String update_time;
}
