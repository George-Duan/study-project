package com.study.jiuyan;

import com.baomidou.mybatisplus.extension.api.R;
import com.google.gson.Gson;
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
        put("Token", "a09c072cd935a23e69b06478e319ed58");
    }};

    public ResResultVO requestJiuYan(JiuYanParam param) throws Exception {
        String url = "https://app.jiuyangongshe.com/jystock-app/api/v1/action/field";
        //        System.out.println(resResultVO);
        return OkHttpUtil.requestPostByHttpClient(url, new Gson().toJson(param, JiuYanParam.class), JiuYanAPI.headers);
    }

//    public static void main(String[] args) {
//        try {
//            JiuYanParam param = new JiuYanParam();
//            param.setDate("2023-12-15");
//            param.setPc(1);
//            new JiuYanAPI().requestJiuYan(param);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
