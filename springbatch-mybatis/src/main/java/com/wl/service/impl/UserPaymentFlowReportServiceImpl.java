package com.wl.service.impl;

import com.wl.mapper.UserPaymentFlowReportMapper;
import com.wl.model.UserPaymentReportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <>
 *
 * @author wulei
 * @create 2019/5/1 0001 16:54
 * @since 1.0.0
 */
@Service("userPaymentFlowReportService")
public class UserPaymentFlowReportServiceImpl implements com.wl.service.UserPaymentFlowReportService {
    @Autowired
    private UserPaymentFlowReportMapper userPaymentFlowReportMapper;

    @Override
    public void add(UserPaymentReportEntity userPaymentReportEntity) {
        userPaymentFlowReportMapper.add(userPaymentReportEntity);
    }

    @Override
    public int batchInsert(List<UserPaymentReportEntity> list) {
        return userPaymentFlowReportMapper.batchInsert(list);
    }
}
