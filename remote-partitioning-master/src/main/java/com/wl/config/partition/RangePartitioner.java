//package com.wl.config.partition;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.partition.support.Partitioner;
//import org.springframework.batch.item.ExecutionContext;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * <>
// *
// * @author wulei
// * @date 2019/5/18 0018 10:53
// * @since 1.0.0
// */
//@Slf4j
//public class RangePartitioner implements Partitioner {
//
//    private JdbcTemplate jdbcTemplate;
//    public RangePartitioner(DataSource dataSource) {
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }
//
//    @Override
//    public Map<String, ExecutionContext> partition(int gridSize) {
//        log.info("partition called gridSize= " + gridSize);
//
//        //数据的最小的ID和最大ID
//        Long min = jdbcTemplate.queryForObject("select min(user_id) from user_info", Long.class);
//        Long max = jdbcTemplate.queryForObject("select max(user_id) from user_info", Long.class);
//
//        if (min == null || max ==null) {
//            min = 0L;
//            max = 0L;
//        }
//
//        //计算每个分区被分到的数据条数
//        Long targetSize = (max - min) /gridSize + 1;
//
//        Map<String, ExecutionContext> result = new HashMap<>(10);
//
//        int number = 0;
//        Long start = min;
//        Long end = start + targetSize -1;
//
//       while (start <= max) {
//           ExecutionContext value = new ExecutionContext();
//           result.put("partition" + number, value);
//
//           if (end >= max) {
//               end = max;
//           }
//           value.putLong("minValue", start);
//           value.putLong("maxValue", end);
//
//           start += targetSize;
//           end += targetSize;
//           number++;
//       }
//
//        return result;
//    }
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
