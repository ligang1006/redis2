package net.gaven.redisdemo;

import net.gaven.redisdemo.schedule.ScheduleService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 缓存  排行榜  top N  添加积分  uid查找排行
 *
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("net.gaven.redisdemo.mapper")
public class RedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
//        new ScheduleService().testSchedule();
    }

}
