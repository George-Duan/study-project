package com.study;

import cn.hutool.core.thread.ThreadUtil;
import com.study.entity.JiuYanAbnormal;
import com.study.entity.TradeCal;
import com.study.jiuyan.*;
import com.study.service.JiuYanAbnormalService;
import com.study.service.TradeCalService;
import com.study.util.TimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JiuYanApiTest{

    @Autowired
    private TradeCalService tradeCalService;
    @Autowired
    private JiuYanAbnormalService jiuYanAbnormalService;

    @Test
    public void loadData(){
        List<TradeCal> tradeCalList = tradeCalService.listByOpenTradeOrderByTradeDateDesc();
        for (TradeCal tradeCal : tradeCalList){
            try{
                System.out.println("开始爬取:" + tradeCal.getCalDate() + "数据");
                Integer tradeDate = TimeUtils.getDateFormat2(tradeCal.getCalDate());
                if (tradeDate < TimeUtils.getDateFormat("2020-06-30")) {
                    return;
                }
                JiuYanParam param = new JiuYanParam();
                param.setDate(TimeUtils.formatDate2(tradeDate));
                param.setPc(1);
                ResResultVO result = new JiuYanAPI().requestJiuYan(param);
                List<JiuYanAbnormal> abnormalList = new ArrayList<>();
                if ("0".equals(result.getErrCode()) && result.getData()!=null && !result.getData().isEmpty()) {
                    List<TypeInfoVO> data = result.getData();
                    for (TypeInfoVO typeInfoVO : data) {
                        if ("简图".equals(typeInfoVO.getName())) {
                            continue;
                        }
                        List<StockInfoVO> list = typeInfoVO.getList();
                        for (StockInfoVO stockInfoVO : list) {

                            JiuYanAbnormal abnormal = new JiuYanAbnormal();
                            abnormal.setCode(stockInfoVO.getCode());
                            abnormal.setName(stockInfoVO.getName());
                            abnormal.setAbnormalName(typeInfoVO.getName());
                            abnormal.setAbnormalReason(typeInfoVO.getReason());
                            String articleCreateTime = stockInfoVO.getArticle().getCreate_time();
                            if (articleCreateTime != null && !articleCreateTime.isEmpty()) {
                                abnormal.setCreateTime(TimeUtils.getDateTimeFormat(articleCreateTime));
                            }
                            abnormal.setTradeDate(typeInfoVO.getDate());
                            abnormal.setPrice(stockInfoVO.getArticle().getAction_info().getPrice());
                            abnormal.setSharesRange(stockInfoVO.getArticle().getAction_info().getShares_range());
                            abnormal.setLimitNum(stockInfoVO.getArticle().getAction_info().getNum());
                            abnormal.setLimitTime(stockInfoVO.getArticle().getAction_info().getTime());
                            abnormal.setArticleExpound(stockInfoVO.getArticle().getAction_info().getExpound());
                            abnormalList.add(abnormal);
                        }
                    }
                    if (!abnormalList.isEmpty()) {
                        jiuYanAbnormalService.saveBatch(abnormalList);
                    }
                }
                ThreadUtil.sleep(1000 * 5L);
            }catch (Exception e){
                e.printStackTrace();
                return;
            }

        }

    }
}