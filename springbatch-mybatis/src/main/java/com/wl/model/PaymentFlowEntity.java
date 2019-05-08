package com.wl.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <>
 *
 * @author wulei
 * @create 2019/4/30 0030 20:38
 * @since 1.0.0
 */
@Data
@Setter
@Getter
@TableName("payment_flow")
public class PaymentFlowEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long paymentFlowId;
    private String orderId;
    private Long userId;
    private String paymentChannel;
    private BigDecimal paymentAmount;
    private String paymentState;
    private Date createDate;
}
