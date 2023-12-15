package com.study.jdk.threads.futures;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class CalculatePriceJava8FromActionBook {

    public Future<Double> getPriceAsync(String product){
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread( () -> {
            double price = calculatePrice(product);
            futurePrice.complete(price);
        }).start();
        return futurePrice;
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public void test(){
        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 0;
            System.out.println("运行结果：" + i);
            //return i;
            return null;
        }, null).whenComplete((res, exception) -> {
            //虽然能得到异常信息，但是没法修改返回数据
            System.out.println("异步任务成功完成了...结果是：" + res + "异常是：" + exception);
        }).exceptionally(throwable -> {
            //可以感知异常，同时返回默认值
            //return 10;
            return null;
        });
    }


}
