package com.wjx.study.controller;

import com.wjx.common.Result;
import com.wjx.common.vo.GoodsVO;
import com.wjx.study.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Desc: 商品服务
 * @File name：com.wjx.study.controller.GoodsController
 * @Create on：2022/3/8 10:52
 * @Author：wjx
 */
@Api(tags = {"商品服务"})
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "查询商品详情")
    @GetMapping(value = "/detail")
    public Result<GoodsVO> selectGoodsById(@RequestParam("goodsId") Long id){
        return Result.ok(goodsService.selectGoodsById(id));
    }

    /**
     * @Des
     * @Date 2022/3/13 16:26
     * @Param
     * @Return
     * @Author wjx
     */
    @ApiOperation(value = "查询商品详情2，不从数据库中查询")
    @GetMapping(value = "/detail2")
    public Result<GoodsVO> selectGoodsById2(@RequestParam("goodsId") Long id){
        GoodsVO goodsVO = new GoodsVO();
        goodsVO.setId(10000000L);
        goodsVO.setName("商品服务写死的商品数据");
        goodsVO.setStock(0);
        goodsVO.setSalePrice(BigDecimal.ZERO);
        return Result.ok(goodsVO);
    }
}
