package com.study.jiuyan;

import lombok.Data;

@Data
public class StockInfoVO {
    private String code;
    private String name;
    private ArticleVO article;
}
