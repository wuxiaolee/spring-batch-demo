package com.wl.config.paymentFlowBatchConfig;

import com.wl.model.PaymentFlowEntity;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

/**
 * <自定义Reader>
 *
 * @author wulei
 * @create 2019/5/1 0001 11:10
 * @since 1.0.0
 */
@Component
public class PaymentFlowReader implements ItemReader<PaymentFlowEntity> {
    @Override
    public PaymentFlowEntity read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        return null;
    }
}
