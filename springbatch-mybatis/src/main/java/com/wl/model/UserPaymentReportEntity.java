package com.wl.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <>
 *
 * @author wulei
 * @create 2019/4/30 0030 20:44
 * @since 1.0.0
 */
@Data
@TableName("user_payment_Report")
public class UserPaymentReportEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private Long userId;
    private Date statisticsDate;
    private BigDecimal amount;
}
