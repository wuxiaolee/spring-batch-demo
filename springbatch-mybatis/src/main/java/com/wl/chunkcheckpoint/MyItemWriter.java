package com.wl.chunkcheckpoint;

import org.springframework.stereotype.Component;

import javax.batch.api.chunk.AbstractItemWriter;
import java.util.List;

/**
 * <>
 *
 * @author wulei
 * @create 2019/5/12 0012 19:44
 * @since 1.0.0
 */
@Component
public class MyItemWriter extends AbstractItemWriter {
    @Override
    public void writeItems(List<Object> items) throws Exception {
        System.out.println(items);
    }
}
