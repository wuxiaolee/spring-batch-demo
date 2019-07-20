/*
 * Copyright (c), 2019-2020, D.W. Data Technology Co.,Ltd.
 */
package com.wl.processor;

import com.wl.model.PaymentFlowEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Neil
 * @date 2019/7/16 0016 23:23
 * @since 1.0.0
 */
@Configuration
public class DemoProcessor implements ItemProcessor<List<PaymentFlowEntity>, PaymentFlowEntity> {


    @Override
    public PaymentFlowEntity process(List<PaymentFlowEntity> item) throws Exception {

        System.out.println("item size " + item.size());
        System.out.println("process item" + item.toString());

        return null;
    }
}
