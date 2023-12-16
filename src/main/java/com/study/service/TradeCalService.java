package com.study.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.dao.TradeCalMapper;
import com.study.entity.TradeCal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeCalService extends ServiceImpl<TradeCalMapper, TradeCal> {

    public List<TradeCal> listByOpenTradeOrderByTradeDateDesc(){
        return this.lambdaQuery().eq(TradeCal::getIsOpen, Boolean.TRUE).isNotNull(TradeCal::getPreTradeDate).orderByDesc(TradeCal::getCalDate).list();
    }
}
