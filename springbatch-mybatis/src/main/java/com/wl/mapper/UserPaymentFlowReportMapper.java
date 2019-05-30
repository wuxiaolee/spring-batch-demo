package com.wl.mapper;

import com.wl.model.UserPaymentReportEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <>
 *
 * @Author wulei
 * @Date 2019/5/1 0001 10:46
 */
@Mapper
public interface UserPaymentFlowReportMapper<T> extends MysqlCommonMapper<T> {
    /**
     * 添加单条记录
     * @param userPaymentReportEntity
     * @return
     */
    void add(UserPaymentReportEntity userPaymentReportEntity);

    /**
     * 添加批量记录
     * @param userPaymentReportEntities
     * @return
     */
    int batchInsert(List<UserPaymentReportEntity> userPaymentReportEntities);

    /**
     * find ...
     * @return
     */
    UserPaymentReportEntity queryUserPaymentFlowReport();
}
