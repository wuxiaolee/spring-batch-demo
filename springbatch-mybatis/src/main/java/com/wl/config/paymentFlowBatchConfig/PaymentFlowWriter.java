package com.wl.config.paymentFlowBatchConfig;

import com.wl.mapper.PaymentFlowMapper;
import com.wl.mapper.UserPaymentFlowReportMapper;
import com.wl.model.UserPaymentReportEntity;
import com.wl.service.impl.UserPaymentFlowReportServiceImpl;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <>
 *
 * @author wulei
 * @create 2019/5/1 0001 18:17
 * @since 1.0.0
 */
@Component
public class PaymentFlowWriter<T> implements ItemWriter<T> {

    private UserPaymentFlowReportMapper reportMapper;

    public PaymentFlowWriter(UserPaymentFlowReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    @Override
    public void write(List<? extends T> items) throws Exception {
        items.forEach(userPaymentReportEntity -> {
            reportMapper.add(userPaymentReportEntity);
        });
    }
}
