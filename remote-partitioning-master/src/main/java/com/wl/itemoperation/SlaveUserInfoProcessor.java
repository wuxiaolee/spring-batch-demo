package com.wl.itemoperation;

import com.wl.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

/**
 * <>
 *
 * @author wulei
 * @date 2019/5/18 0018 14:38
 * @since 1.0.0
 */
@Service
public class SlaveUserInfoProcessor implements ItemProcessor<UserInfo, UserInfo> {

    private static final Logger log = LoggerFactory.getLogger(SlaveUserInfoProcessor.class);

    @Override
    public UserInfo process(UserInfo item) throws Exception {
        log.info("【{}】经过处理器", item.getUserId());
        return item;
    }
}
