package com.wl.service;

import com.wl.model.PaymentFlowEntity;
import com.wl.model.UserPaymentReportEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentFlowServiceImplTest {

    @Autowired
    private PaymentFlowServiceImpl paymentFlowService;

    @Autowired
    private UserPaymentFlowReportServiceImpl userPaymentFlowReportServiceImpl;

    @Test
    public void saveBatch() {
        List<PaymentFlowEntity> paymentFlows = new ArrayList<>();

        for (int i = 0; i < 5000 ; i++) {
            String orderId = "300" + i;
            PaymentFlowEntity paymentFlow = new PaymentFlowEntity();
            paymentFlow.setUserId(3L);
            paymentFlow.setOrderId(orderId);
            paymentFlow.setPaymentChannel("alipay");
            paymentFlow.setPaymentState("paid");
            paymentFlow.setPaymentAmount(new BigDecimal(100));
            paymentFlow.setCreateDate(new Date());

            paymentFlows.add(paymentFlow);
        }

        Long startTime = System.currentTimeMillis();
        System.out.println("开始插入="+startTime);
        paymentFlowService.addAll(paymentFlows);
        System.out.println("插入结束，耗时："+(System.currentTimeMillis()-startTime) +"ms");
    }

    @Test
    public void addPaymentFlow() {
        String orderId = "10000000";
        PaymentFlowEntity paymentFlow = new PaymentFlowEntity();
        paymentFlow.setUserId(4L);
        paymentFlow.setOrderId(orderId);
        paymentFlow.setPaymentChannel("alipay");
        paymentFlow.setPaymentState("paid");
        paymentFlow.setPaymentAmount(new BigDecimal(100));
        paymentFlow.setCreateDate(new Date());

        paymentFlowService.add(paymentFlow);
    }

    @Test
    public void addUserPaymentFlowReport() {
        UserPaymentReportEntity reportEntity = new UserPaymentReportEntity();
        reportEntity.setId(1L);
        reportEntity.setUserId(1L);
        reportEntity.setAmount(new BigDecimal(1000));
        reportEntity.setStatisticsDate(new Date());

        userPaymentFlowReportServiceImpl.add(reportEntity);
    }


}