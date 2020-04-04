package net.gaven.redisdemo.controller;

import net.gaven.redisdemo.domain.ScoreFlow;
import net.gaven.redisdemo.mapper.ScoreFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: redis-demo
 * @description:
 * @author: Mr.lee
 * @create: 2020-04-03 12:03
 **/
@RestController
public class TestController {
    @Autowired
    private ScoreFlowMapper scoreFlow;
    @GetMapping("/test")
    public Object test(Integer id){
        ScoreFlow scoreFlow = this.scoreFlow.selectByPrimaryKey(id);
        return scoreFlow;
    }
}
