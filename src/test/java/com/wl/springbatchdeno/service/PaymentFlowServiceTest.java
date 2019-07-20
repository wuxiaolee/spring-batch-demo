package com.wl.springbatchdeno.service;

import com.wl.springbatchdemo.entity.PaymentFlow;
import com.wl.springbatchdemo.service.PaymentFlowService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentFlowServiceTest {

    @Autowired
    private PaymentFlowService paymentFlowService;

    @Test
    public void add() {

        List<PaymentFlow> paymentFlows = new ArrayList<>();

        for (int i = 0; i < 5000 ; i++) {
            String orderId = "100" + i;
            PaymentFlow paymentFlow = new PaymentFlow();
            paymentFlow.setUserId(1L);
            paymentFlow.setOrderId(orderId);
            paymentFlow.setPaymentChannel("wxpay");
            paymentFlow.setPaymentState("paid");
            paymentFlow.setPaymentAmount(new BigDecimal(100));

            paymentFlows.add(paymentFlow);
        }

        paymentFlowService.add(paymentFlows);
    }
}