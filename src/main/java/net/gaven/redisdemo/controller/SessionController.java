package net.gaven.redisdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: redis-demo
 * @description:
 * @author: Mr.lee
 * @create: 2020-04-02 16:40
 **/
@RestController
public class SessionController {

    @GetMapping("/setSession")
    public Map<String,Object> setSession(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        System.out.println("url"+request.getRequestURL());
        System.out.println("uri"+request.getRequestURI());
        request.getSession().setAttribute("request URL",request.getRequestURL());
        map.put("request Url",request.getRequestURL());
        return map;
    }
    @GetMapping("/getSession")
    public Object getSession(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        map.put("sessionIDUrl",request.getSession().getAttribute("request Url"));
        map.put("sessionId",request.getSession().getId());
        return map;
    }
}
