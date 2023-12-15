package com.study.jiuyan;

import lombok.Data;

@Data
public class ArticleVO {

    private String user_id;
    private String title;
    private UserVO user;
    private Integer step_count;
    private Integer like_count;
    private Integer is_step;
    private Integer is_like;
    private Integer forward_count;
    private String create_time;
    private Integer comment_count;
    private String article_id;
    private ActionInfoVO action_info;
}
