package com.wl.localpartitioning.itemoperation;

import com.wl.localpartitioning.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <>
 *
 * @author wulei
 * @date 2019/5/18 0018 14:41
 * @since 1.0.0
 */
@Slf4j
@Component
public class SlaveUserInfoWriter<T> implements ItemWriter<T> {
    @Override
    public void write(List<? extends T> items) throws Exception {
        items.forEach(i -> {
            UserInfo userInfo = (UserInfo) i;
            log.info("userId={}", userInfo.getUserId());
        });
    }
}
