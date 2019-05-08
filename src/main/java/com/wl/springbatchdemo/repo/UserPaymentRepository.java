package com.wl.springbatchdemo.repo;

import com.wl.springbatchdemo.entity.UserPaymentReport;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <>
 *
 * @author wulei
 * @create 2019/4/27 0027 21:09
 * @since 1.0.0
 */
public interface UserPaymentRepository extends  JpaRepository<UserPaymentReport, Long> {
}
