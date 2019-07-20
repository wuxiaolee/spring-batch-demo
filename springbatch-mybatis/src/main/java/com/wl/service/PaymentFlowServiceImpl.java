package com.wl.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wl.mapper.PaymentFlowMapper;
import com.wl.model.PaymentFlowEntity;
import com.wl.service.PaymentFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <>
 *
 * @author wulei
 * @create 2019/4/30 0030 20:49
 * @since 1.0.0
 */
@Service("paymentFlowService")
public class PaymentFlowServiceImpl implements PaymentFlowService {

    @Autowired
    private PaymentFlowMapper paymentFlowMapper;

    @Override
    public int addAll(List<PaymentFlowEntity> list) {
        return paymentFlowMapper.batchInsert(list);
    }

    @Override
    public Map<String, Object> queryPaymentFlow() {
        return paymentFlowMapper.queryPaymentFlow();
    }

    @Override
    public void add(PaymentFlowEntity paymentFlowEntity) {
        paymentFlowMapper.add(paymentFlowEntity);
    }

    @Override
    public void update(PaymentFlowEntity paymentFlowEntity) {
        paymentFlowMapper.updatePaymentFlow(paymentFlowEntity);
    }


}
