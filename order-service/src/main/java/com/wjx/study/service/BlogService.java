package com.wjx.study.service;

import com.wjx.common.dto.BlogLikeDTO;
import com.wjx.common.dto.BlogTop5DTO;
import com.wjx.common.vo.UserVO;

import java.util.List;

/**
 * @Desc: 博客服务
 * @File name：com.wjx.study.service.BlogService
 * @Create on：2022/4/2 11:00
 * @Author：wjx
 */
public interface BlogService {

    /**
     * @Des 点赞
     * @Date 2022/4/2 11:02
     * @Param
     * @Return true表示点赞成功，false表示点赞失败
     * @Author wjx
     */
    boolean like(BlogLikeDTO dto);

    /**
     * @Des 点赞2，使用redis的SortedSet数据类型，方便按时间查最早点赞的N个人
     * @Date 2022/4/2 12:43
     * @Param
     * @Return
     * @Author wjx
     */
    boolean like2(BlogLikeDTO dto);

    /**
     * @Des 查询最早点赞的5个用户的信息
     * @Date 2022/4/2 13:30
     * @Param
     * @Return
     * @Author wjx
     */
    List<UserVO> likeTop5(BlogTop5DTO dto);

    /**
     * @Des 分页查询关注自己的粉丝列表， ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
     * @Date 2022/4/2 16:33
     * @Param max为上一次的最小分数值(第一次为当前时间戳)
     * @Param offset为等于max的偏移量的个数(第一次为0，之后为上次最小值的重复数量)
     * @Return
     * @Author wjx
     */
    List<UserVO> queryBlogOfFollow(Long max, Integer offset);
}
