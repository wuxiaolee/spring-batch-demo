package com.wl.mapper;

import com.wl.model.PaymentFlowEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <>
 *
 * @author wulei
 * @create 2019/4/30 0030 20:53
 * @since 1.0.0
 */
@Mapper
public interface PaymentFlowMapper {

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(List<PaymentFlowEntity> list);

    /**
     * 查询所有记录
     * @return
     */
    Map<String, Object> queryPaymentFlow();

    void add(PaymentFlowEntity paymentFlowEntity);

    void updatePaymentFlow(PaymentFlowEntity paymentFlowEntity);

    List<PaymentFlowEntity> findList(@Param("page") Integer page, @Param("size") Integer size);

    int count();

}
