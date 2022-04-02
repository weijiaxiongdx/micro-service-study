package com.wjx.study.controller;

import com.wjx.common.Result;
import com.wjx.common.dto.BlogLikeDTO;
import com.wjx.common.dto.BlogTop5DTO;
import com.wjx.common.vo.UserVO;
import com.wjx.study.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Desc: 博客服务类
 * @File name：com.wjx.study.controller.BlogController
 * @Create on：2022/4/2 10:59
 * @Author：wjx
 */
@Api(tags = {"博客服务"})
@RestController
@RequestMapping(value = "/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @ApiOperation(value = "点赞")
    @PostMapping(value = "/like")
    public Result<Boolean> like(@RequestBody @Valid BlogLikeDTO dto){
        dto.setUserId(1L);
        boolean result = blogService.like(dto);
        return Result.ok(result);
    }

    @ApiOperation(value = "点赞2")
    @PostMapping(value = "/like2")
    public Result<Boolean> like2(@RequestBody @Valid BlogLikeDTO dto) {
        dto.setUserId(3L);
        boolean result = blogService.like2(dto);
        return Result.ok(result);
    }

    @ApiOperation(value = "查询最早点赞的5个用户的信息")
    @PostMapping(value = "/likeTop5")
    public Result<List<UserVO>> likeTop5(@RequestBody @Valid BlogTop5DTO dto) {
        List<UserVO> userList = blogService.likeTop5(dto);
        return Result.ok(userList);
    }

    @ApiOperation(value = "分页查询关注自己的粉丝列表")
    @GetMapping(value = "/queryBlogOfFollow")
    public Result<List<UserVO>> queryBlogOfFollow(@RequestParam(value = "lastId") Long max, @RequestParam(value = "offset",defaultValue = "0") Integer offset) {
        List<UserVO> userList = blogService.queryBlogOfFollow(max,offset);
        return Result.ok(userList);
    }
}
