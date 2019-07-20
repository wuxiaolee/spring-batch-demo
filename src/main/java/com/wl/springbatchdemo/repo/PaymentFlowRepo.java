package com.wl.springbatchdemo.repo;

import com.wl.springbatchdemo.entity.PaymentFlow;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <>
 *
 * @Author wulei
 * @Date 2019/4/29 0029 21:24
 */
public interface PaymentFlowRepo extends JpaRepository<PaymentFlow, Long> {
}
