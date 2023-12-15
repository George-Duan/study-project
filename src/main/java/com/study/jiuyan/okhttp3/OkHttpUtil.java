package com.study.jiuyan.okhttp3;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.study.dto.HttpResultDTO;
import com.study.jiuyan.ResResultVO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.Map;

@Slf4j
public class OkHttpUtil {

    private static OkHttpClient okHttpClient;

    static {
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.201", 48897));
//        ProxyConfig proxyConfig = new ProxyConfig();
//        proxyConfig.setProxy(proxy);
//        proxyConfig.setAuthentication(null);
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(log::info);//创建拦截对象
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这一句一定要记得写，否则没有数据输出
//        okHttpClient = new OkHttpBBuilder().setProxyConfig(proxyConfig).setTimeout(30).setLogInterceptor(logInterceptor).build().build(); //.setProxyConfig(proxyConfig)//.setSslTrust(true)
        okHttpClient = new OkHttpBBuilder().setTimeout(30).setLogInterceptor(logInterceptor).build().build();
    }


    public static ResResultVO requestPostByHttpClient(String url, String params, Map<String, String> headerMap) throws Exception{
        Request.Builder builder = new Request.Builder().url(url);
        if (headerMap != null) {
            for (String key : headerMap.keySet()){
                builder = builder.addHeader(key, headerMap.get(key));
            }
        }
        Request request = builder
                .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"), params))
                .build();
        Call call = okHttpClient.newCall(request);
        try (Response response = call.execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String responseBodyString = responseBody.string();
                System.out.println(responseBodyString);
//                return JSONObject.parseObject(responseBodyString, new TypeReference<ResResultVO>(){});
                return new Gson().fromJson(responseBodyString, new TypeToken<ResResultVO>(){}.getType());
            } else {
                throw new Exception("request " + url + " commonExecute ResponseBody is null");
            }
        }
    }

    public static HttpResultDTO requestPostByHttpClient(String url, String path, String params, Map<String, String> headerMap) throws Exception{
        HttpResultDTO httpResultDTO;
        String requestUrl = url + path;
        Request.Builder builder = new Request.Builder().url(requestUrl);
        if (headerMap != null) {
            for (String key : headerMap.keySet()){
                builder = builder.addHeader(key, headerMap.get(key));
            }
        }
        Request request = builder
                .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"), params))
                .build();
        Call call = okHttpClient.newCall(request);
        try (Response response = call.execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String responseBodyString = responseBody.string();
                log.info(responseBodyString);
                httpResultDTO = JSONObject.parseObject(responseBodyString, HttpResultDTO.class);
                return httpResultDTO;
            } else {
                throw new Exception("request " + requestUrl + " commonExecute ResponseBody is null");
            }
        }
    }

}
