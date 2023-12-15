package com.study.jiuyan;

import lombok.Data;

import java.util.List;

@Data
public class TypeInfoVO {

    private String action_field_id;
    private Integer count;
    private String create_time;
    private String date;
    private String delete_time;
    private String is_delete;
    private List<StockInfoVO> list;
    private String name;
    private String reason;
    private Integer sort_no;
    private Integer status;
    private String update_time;
}
