package com.wl.chunkcheckpoint;

import org.springframework.batch.item.ItemProcessor;

/**
 * <>
 *
 * @author wulei
 * @create 2019/5/12 0012 19:42
 * @since 1.0.0
 */
public class MyItemProcessor implements ItemProcessor {
    @Override
    public MyOutputRecord process(Object t) {
        System.out.println("processItem: " + t);

        return (((MyInputRecord) t).getId() % 2 == 0) ? null : new MyOutputRecord(((MyInputRecord) t).getId() * 2);
    }
}
