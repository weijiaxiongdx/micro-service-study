package com.wjx.study.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.wjx.common.Result;
import com.wjx.common.dto.BlogLikeDTO;
import com.wjx.common.dto.BlogTop5DTO;
import com.wjx.common.vo.UserVO;
import com.wjx.study.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Desc: 博客服务
 * @File name：com.wjx.study.service.impl.BlogServiceImpl
 * @Create on：2022/4/2 11:02
 * @Author：wjx
 */
@Slf4j
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @Des 点赞
     * @Date 2022/4/2 11:02
     * @Param
     * @Return true表示点赞成功，false表示点赞失败
     * @Author wjx
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean like(BlogLikeDTO dto) {
        Long userId = dto.getUserId();

        //判断当前用户是否已经给当前博客点赞过
        String key = "blog:liked:" + dto.getId();//博客点赞集合key，存储给这个博客点过赞的用户id
        Boolean result = stringRedisTemplate.opsForSet().isMember(key,userId.toString());
        if(BooleanUtil.isFalse(result)){
            // 未点赞则点赞
            //操作数据库，给点赞数加1等 todo

            //数据库修改成功后，把用户id添加到redis对应的集合中
            stringRedisTemplate.opsForSet().add(key, userId.toString());
            return true;
        } else {
            // 已点赞则取消点赞
            //操作数据库，给点赞数减1等 todo

            //数据库修改成功后，把用户id从redis对应的集合中删除
            stringRedisTemplate.opsForSet().remove(key, userId.toString());
            return false;
        }
    }

    /**
     * @Des 点赞2，使用redis的SortedSet数据类型，方便按时间查最早点赞的N个人
     * @Date 2022/4/2 12:44
     * @Param
     * @Return true表示点赞成功，false表示点赞失败
     * @Author wjx
     */
    @Override
    public boolean like2(BlogLikeDTO dto){
        Long userId = dto.getUserId();

        //判断当前用户是否已经给当前博客点赞过
        String key = "blog:liked:" + dto.getId();//博客点赞集合key，存储给这个博客点过赞的用户id
        Double score = stringRedisTemplate.opsForZSet().score(key,userId.toString());//key不能与其它类型的key名称一样，否则报错WRONGTYPE Operation against a key holding the wrong kind of value
        if(score == null){
            // 未点赞则点赞
            //操作数据库，给点赞数加1等 todo

            //数据库修改成功后，把用户id添加到redis对应的集合中,且设置当前时间为排行分数
            stringRedisTemplate.opsForZSet().add(key,userId.toString(),System.currentTimeMillis());
            return true;
        } else {
            // 已点赞则取消点赞
            //操作数据库，给点赞数减1等 todo

            //数据库修改成功后，把用户id从redis对应的集合中删除
            stringRedisTemplate.opsForZSet().remove(key, userId.toString());
            return false;
        }
    }

    /**
     * @Des 查询最早点赞的5个用户的信息(5个人的用户信息需要按照redis中用户顺序返回) 与like2方法配合使用
     * @Date 2022/4/2 13:30
     * @Param
     * @Return
     * @Author wjx
     */
    @Override
    public List<UserVO> likeTop5(BlogTop5DTO dto){

        // 从redis查询top5的点赞用户的id
        String key = "blog:liked:" + dto.getId();
        Set<String> top5UserIdSet = stringRedisTemplate.opsForZSet().range(key,0,4);//闭区间
        if(CollectionUtils.isEmpty(top5UserIdSet)){
            return null;
        }

        List<Long> idList = top5UserIdSet.stream().map(Long::valueOf).collect(Collectors.toList());
        log.info("从redis中查到的最早的5个用户为:{}",idList);
        // 根据用户id集合idList从数据库中批量查询用户信息 todo
        // 为了保证按查询条件顺序返回，sql中in和field的顺序要一致。java中可传idList和idList拼接好的字符串到sql语句中
        // select * from user where id in (?,?,?,?,?) order by field(id,?,?,?,?,?)

        // 返回查询到的用户信息
        List<UserVO> userList = new ArrayList(){{
            add(new UserVO(1L,"张三","18312478432"));
            add(new UserVO(2L,"李四","18312470987"));
            add(new UserVO(3L,"王五","15612470987"));
            add(new UserVO(4L,"赵六","13452470987"));
            add(new UserVO(5L,"钱七","18912470987"));
        }};

        return userList;
    }

    /**
     * @Des 分页查询关注自己的粉丝列表，ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
     * @Date 2022/4/2 16:35
     * @Param max为上一次的最小分数值(第一次为当前时间戳)
     * @Param offset为等于max的偏移量的个数(第一次为0，之后为上次最小值的重复数量)
     * @Return
     * @Author wjx
     */
    @Override
    public List<UserVO> queryBlogOfFollow(Long max, Integer offset){
        Long currentUserId = 1L;
        //其它业务逻辑1 todo

        Set<ZSetOperations.TypedTuple<String>> set = stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores("key",0,max,offset,2);
        long minTime = 0;//最小时间
        int os = 1;//最小时间重复的次数
        for (ZSetOperations.TypedTuple<String> Tuple : set){
            String idStr = Tuple.getValue();
            long time = Tuple.getScore().longValue();
            if(time == minTime){
                os++;
            } else {
                // 不是最小时间则重置最小时间和重复次数
                minTime = time;
                os = 1;
            }
        }

        //其它业务逻辑2，查数据库中的数据时有likeTop5中同样的顺序问题，解决方法一致 todo
        //最终封装返回结果，包括要查的数据、最小时间minTime(下一次查询的参数)、最小时间重复次数(下一次查询的参数)
        return null;
    }

    /**
     * @Des 用户签到，key一般为用户id加上年月，方便统计
     * @Date 2022/4/3 10:45
     * @Param
     * @Return true表示签到成功，false表示签到失败
     * @Author wjx
     */
    @Override
    public boolean sign(){
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String key = "sign:" + userId + ":" + date;
        stringRedisTemplate.opsForValue().setBit(key,now.getDayOfMonth()-1,true);
        return true;
    }

    /**
     * @Des 统计当月连续签到次数：从最后一次签到开始向前统计，一直遇到第一次未签到为止，计算总的签到次数，就是连续签到次数
     * @Date 2022/4/3 13:50
     * @Param 
     * @Return 
     * @Author wjx
     */
    @Override
    public Integer countSignNum(){
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String key = "sign:" + userId + ":" + date;
        int dayOfMonth = now.getDayOfMonth();

        //获取本月截至今天为止的所有签到记录，返回的是一个十进制数字，redis命令为BITFIELD sign:1:202204 get u14 0
        //u表示无符号、14表示查多少个bit位(当前是几号就查多少个)、0表示从第一个位置开始查
        List<Long> signList = stringRedisTemplate.opsForValue().bitField(key, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0));
        if(signList == null || signList.isEmpty()){
            return 0;
        }

        //因为只有一个get子命令，所以最多只有一条数据，即当月的所有签到记录对应的十进制数字
        Long num = signList.get(0);
        if(num == null || num == 0){
            return 0;
        }

        int count = 0;
        while (true) {
            //与1做与运算，得到这个数字的最后一个bit位，判断这个bit位是否为0，为0则表示未签到
            if((num & 1) == 0){
                // 未签到，则结束
                break;
            } else {
                //签到，计算器加1
                count++;
            }

            //把数字右移一位，去掉最后一位已判断过的bit位，继续下一个bit位判断
            num >>>= 1;
        }

        return count;
    }
}
