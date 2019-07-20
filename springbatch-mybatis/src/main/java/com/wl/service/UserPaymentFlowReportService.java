package com.wl.service;

import com.wl.model.UserPaymentReportEntity;

import java.util.List;

/**
 * <>
 *
 * @Author wulei
 * @Date 2019/5/1 0001 16:54
 */
public interface UserPaymentFlowReportService {

    /**
     * 添加单条记录
     * @param userPaymentReportEntity
     */
    void add(UserPaymentReportEntity userPaymentReportEntity);

    /**
     * 批量添加记录
     * @param list
     * @return
     */
    int batchInsert(List<UserPaymentReportEntity> list);
}
