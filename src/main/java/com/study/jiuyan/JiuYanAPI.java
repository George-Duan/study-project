package com.study.jiuyan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.study.jiuyan.okhttp3.OkHttpUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public class JiuYanAPI {

    public static Map<String, String> headers = new HashMap(){{
        put("Content-Type", "application/json");
        put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,ko;q=0.7");
        put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        put("Platform", "3");
        put("Timestamp", System.currentTimeMillis()+"");
        put("Token", "03388b6f55c3f23287ee334f12f9c90b");
//        put("Content-Type", "application/json");
//        put("Content-Type", "application/json");
//        put("Content-Type", "application/json");
//        put("Content-Type", "application/json");


    }};

    public void requestJiuYan() throws Exception {
        String url = "https://app.jiuyangongshe.com/jystock-app/api/v1/action/field";
        Param param = new Param();
        param.setDate("2023-12-15");
        param.setPc(1);
//        TypeReference<ResResultVO> typeReference = new TypeReference<ResResultVO>();
        ResResultVO resResultVO = OkHttpUtil.requestPostByHttpClient(url, JSON.toJSON(param).toString(), JiuYanAPI.headers);
        System.out.println(resResultVO);
    }

    public static void main(String[] args) {
        try {
            new JiuYanAPI().requestJiuYan();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    class Param{
        String date;
        Integer pc;
    }

}
