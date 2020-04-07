package net.gaven.redisdemo.schedule;

import net.gaven.redisdemo.util.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @program: redis-demo
 * @description: lua 脚本
 * @author: Mr.lee
 * @create: 2020-04-04 12:31
 **/
@Service
public class LuaDistbuteLock {
    private static final Logger logger= LoggerFactory.getLogger(LuaDistbuteLock.class);

    @Resource
    private RedisService redisService;

    @Autowired
    private RedisTemplate redisTemplate;

    private static String LOCK_PREFIX="lua_";

    private DefaultRedisScript<Boolean> lockScript;

//    @Scheduled(cron = " 0/10 * * * * *")
    public void lockJob(){
        String lock=LOCK_PREFIX +"LockNxExJob";

        boolean luaRet=false;
        try {
            //keys[0] keys[1]
            luaRet=luaExpress(lock,getHostIp());
            /**
             * 获取锁失败
             */
            if(!luaRet){
                String usedIp = (String) redisService.genValue(lock);
                logger.info("lua gat lock fail,lock belong to:{}",usedIp);
                return;
            }else {
                logger.info("get lock success");
                Thread.sleep(5000);
            }

        }catch (Exception e){
            logger.error("lock err"+e);
        }finally {
            if (luaRet){
                logger.info("release lock success");
                redisTemplate.delete(lock);
            }
        }
    }

    /**
     * 获取lua结果
     * @param key
     * @param value
     * @return
     */
    private boolean luaExpress(String key, String value) {
        lockScript=new DefaultRedisScript<Boolean>();

        lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("add.lua")));
        lockScript.setResultType(Boolean.class);
        /**
         * 封装参数
         *
         */
        List<Object> keyList=new ArrayList<>();
        keyList.add(key);
        keyList.add(value);
        Boolean result= (Boolean)redisTemplate.execute(lockScript, keyList);
        return result;
    }

    /**
     * 获取本地ip地址
     * @return 返回ip地址
     */
    private String getHostIp() {
        try{
            Enumeration<NetworkInterface> allNetInterface=NetworkInterface.getNetworkInterfaces();
            while (allNetInterface.hasMoreElements()){
                NetworkInterface networkInterface=(NetworkInterface)allNetInterface.nextElement();
                Enumeration<InetAddress> address=networkInterface.getInetAddresses();
                while (address.hasMoreElements()){
                    InetAddress ip=(InetAddress) address.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":") == -1) {
                        return ip.getHostAddress();
                    }
                }
            }
        }catch (Exception e){

        }
        return null;
    }

}
