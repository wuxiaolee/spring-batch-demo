/*
 * Copyright (c), 2019-2020, D.W. Data Technology Co.,Ltd.
 */
package com.wl.reader;

import com.wl.mapper.PaymentFlowMapper;
import com.wl.model.PaymentFlowEntity;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Neil
 * @date 2019/7/16 0016 23:39
 * @since 1.0.0
 */
@Configuration
public class DemoReader implements ItemReader<List<PaymentFlowEntity>> {

    @Autowired
    private PaymentFlowMapper paymentFlowMapper;

    private int size = 3;
    private int count = 0;
    private int number = 0;
    private int limit = 0;

    @Override
    public List<PaymentFlowEntity> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("-------------");

        if (count == 0) {
            count = paymentFlowMapper.count();
            if (count % 2 == 0) {
                limit = count / 3;
            } else {
                limit = count / 3 + 1;
            }
        }



        List<PaymentFlowEntity> paymentFlowEntities =  paymentFlowMapper.findList(number, size);
        number = number + size;
        if (CollectionUtils.isEmpty(paymentFlowEntities)) {
            count = 0;
            number = 0;
            limit = 0;
            return null;
        }

        return paymentFlowEntities;
    }
}
