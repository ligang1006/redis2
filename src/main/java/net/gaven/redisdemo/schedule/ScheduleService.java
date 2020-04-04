package net.gaven.redisdemo.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @program: redis-demo
 * @description:
 * @author: Mr.lee
 * @create: 2020-04-04 10:06
 **/
//@Service
public class ScheduleService {
    @Scheduled(cron = "0/5 * * * * *")
    public void testSchedule(){
        System.out.println(""+System.currentTimeMillis());
    }
}
