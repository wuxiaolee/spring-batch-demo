package com.wl.springbatchdemo.service;

import com.wl.springbatchdemo.entity.PaymentFlow;
import com.wl.springbatchdemo.repo.PaymentFlowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <制造测试数据>
 *
 * @author wulei
 * @create 2019/4/29 0029 21:25
 * @since 1.0.0
 */
@Service
public class PaymentFlowService {

    @Autowired
    private final PaymentFlowRepo paymentFlowRepo;

    @Autowired
    public PaymentFlowService(PaymentFlowRepo paymentFlowRepo) {
        this.paymentFlowRepo = paymentFlowRepo;
    }

    /**
     * 批量添加流水
     * @param list
     */
    public void add(List<PaymentFlow> list) {
        paymentFlowRepo.saveAll(list);
    }
}
