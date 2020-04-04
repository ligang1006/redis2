package net.gaven.redisdemo.service;

import net.gaven.redisdemo.domain.ScoreFlow;
import net.gaven.redisdemo.domain.User;
import net.gaven.redisdemo.domain.UserScore;
import net.gaven.redisdemo.domain.UserScoreExample;
import net.gaven.redisdemo.mapper.ScoreFlowMapper;
import net.gaven.redisdemo.mapper.UserMapper;
import net.gaven.redisdemo.mapper.UserScoreMapper;
import net.gaven.redisdemo.util.RedisService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: redis-demo
 * @description:
 * @author: Mr.lee
 * @create: 2020-04-02 20:55
 **/

@Service

public class RankService implements InitializingBean {

    @Resource
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ScoreFlowMapper scoreFlowMapper;

    @Autowired
    private UserScoreMapper userScoreMapper;

    private static final String RANKGAME="user_score";

    private static final String SALESCORE = "sale_score_rank:";

    public void addRank(String uid,Integer score){
        redisService.zAdd(RANKGAME,uid,score);
    }

    /**
     *  private Integer id;
     *
     *     private Long score;
     *
     *     private Integer userId;
     * @param uid
     * @param score
     */
    @Transactional
    public void increSocre(String uid, Integer score) {

        redisService.incrementScore(RANKGAME, uid, score);

        ScoreFlow scoreFlow = scoreFlowMapper.selectByPrimaryKey(Integer.parseInt(uid));
//        scoreFlowMapper.updateByPrimaryKey(new ScoreFlow((long)score,Integer.parseInt(uid),scoreFlow.getUserName()));

    }


    public Long rankNum(String uid) {
        return redisService.zRank(RANKGAME, uid);
    }

    public Long score(String uid) {
        Long score = redisService.zSetScore(RANKGAME, uid).longValue();
        return score;
    }

    public Set<ZSetOperations.TypedTuple<Object>> rankWithScore(Integer start, Integer end) {
        return redisService.zRankWithScore(RANKGAME, start, end);
    }

    /**
     *  预热DB加载到redis
     */
    public void rankSaleAdd() {
        UserScoreExample example = new UserScoreExample();
        example.setOrderByClause("id desc");
        List<UserScore> userScores = userScoreMapper.selectByExample(example);
        userScores.forEach(userScore -> {
            String key = userScore.getUserId() + ":" + userScore.getName();
            redisService.zAdd(SALESCORE, key, userScore.getUserScore());
        });
    }


    /**
     * 添加用户积分
     *
     * @param uid
     * @param score
     */
    public void increSaleSocre(String uid, Integer score) {
        User user = userMapper.find(uid);
        if (user == null) {
            return;
        }
        int uidInt = Integer.parseInt(uid);
        long socreLong = Long.parseLong(score + "");
        String name = user.getUserName();
        String key = uid + ":" + name;
        scoreFlowMapper.insertSelective(new ScoreFlow(socreLong, uidInt, name));
        userScoreMapper.insertSelective(new UserScore(uidInt, socreLong, name));
        redisService.incrementScore(SALESCORE, key, Double.parseDouble(score+""));
    }

    /**
     * 通过uid和姓名查询排名
     * @param uid
     * @param name
     * @return
     */
    public Map<String, Object> userRank(String uid, String name) {
        Map<String, Object> retMap = new LinkedHashMap<>();
        String key = uid + ":" + name;
        Integer rank = redisService.zRank(SALESCORE, key).intValue();
        Long score = redisService.zSetScore(SALESCORE, key).longValue();
        retMap.put("userId", uid);
        retMap.put("score", score);
        retMap.put("rank", rank);
        return retMap;
    }

    /**
     * 查找TOP  N
      * @param start
     * @param end
     * @return
     */

    public List<Map<String, Object>> reverseZRankWithRank(long start, long end) {
        Set<ZSetOperations.TypedTuple<Object>> setObj = redisService.reverseZRankWithRank(SALESCORE, start, end);
        List<Map<String, Object>> mapList = setObj.stream().map(objectTypedTuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("userId", objectTypedTuple.getValue().toString().split(":")[0]);
            map.put("userName", objectTypedTuple.getValue().toString().split(":")[1]);
            map.put("score", objectTypedTuple.getScore());
            return map;
        }).collect(Collectors.toList());
        return mapList;
    }
    /**
     * 通过积分倒序排列  高到低    top N
     * @param start
     * @param end
     * @return
     */
    public List<Map<String, Object>> saleRankWithScore(Integer start, Integer end) {
        Set<ZSetOperations.TypedTuple<Object>> setObj = redisService.reverseZRankWithScore(SALESCORE, start, end);
        List<Map<String, Object>> mapList = setObj.stream().map(objectTypedTuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("userId", objectTypedTuple.getValue().toString().split(":")[0]);
            map.put("userName", objectTypedTuple.getValue().toString().split(":")[1]);
            map.put("score", objectTypedTuple.getScore());
            return map;
        }).collect(Collectors.toList());
        return mapList;
    }

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("======enter run bean=======");
//        Thread.sleep(100000);
//        this.rankSaleAdd();
//    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("======enter init bean=======");
        List<User> all = userMapper.findAll();
        for (User user:all){

            System.out.println(user);
            redisService.add("xxxxx",user);


        }


        this.rankSaleAdd();
    }

}
