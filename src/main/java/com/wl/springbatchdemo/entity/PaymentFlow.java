
package com.wl.springbatchdemo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <支付流水表>
 *
 * @author wulei
 * @create 2019/4/27 0027 14:05
 * @since 1.0.0
 */
//@Data
@Entity
@Table(name = "payment_flow")
public class PaymentFlow implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long getPaymentFlowId() {
        return paymentFlowId;
    }

    public void setPaymentFlowId(Long paymentFlowId) {
        this.paymentFlowId = paymentFlowId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentFlowId;
    private String orderId;
    private Long userId;
    private String paymentChannel;
    private BigDecimal paymentAmount;
    private String paymentState;
}
