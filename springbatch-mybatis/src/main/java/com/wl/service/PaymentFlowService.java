package com.wl.service;

import com.wl.model.PaymentFlowEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <制造测试数据>
 *
 * @author wulei
 * @create 2019/4/29 0029 21:25
 * @since 1.0.0
 */
@Transactional(rollbackFor = Exception.class)
public interface PaymentFlowService{

    /**
     * 批量添加流水
     * @param list
     */
    int addAll(List<PaymentFlowEntity> list);

    /**
     * 查询所有记录
     * @return
     */
    Map<String, Object> queryPaymentFlow();

    /**
     * 添加单条记录
     * @param paymentFlowEntity
     */
    void add(PaymentFlowEntity paymentFlowEntity);

    /**
     * 更新单条记录
     * @param paymentFlowEntity
     */
    void update(PaymentFlowEntity paymentFlowEntity);
}
