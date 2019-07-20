package com.wl.chunkcheckpoint;

import javax.batch.api.chunk.AbstractCheckpointAlgorithm;
import java.util.concurrent.CountDownLatch;

/**
 * <>
 *
 * @author wulei
 * @create 2019/5/12 0012 19:44
 * @since 1.0.0
 */
public class MyCheckpointAlgorithm extends AbstractCheckpointAlgorithm {

    public static CountDownLatch checkpointCountDownLatch = new CountDownLatch(10);

    @Override
    public boolean isReadyToCheckpoint() throws Exception {
        checkpointCountDownLatch.countDown();

        return MyItemReader.COUNT % 5 == 0;
    }
}
