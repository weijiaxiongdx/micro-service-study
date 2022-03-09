package com.wjx.study.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Desc: 自定义sentinel限流规则异常处理页面
 * @File name：com.wjx.study.config.ExceptionHandlePage
 * @Create on：2022/3/9 14:32
 * @Author：wjx
 */
@Component
public class ExceptionHandlePage implements BlockExceptionHandler{
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        String result = "调用异常";
        if(e instanceof FlowException){
            result = "接口被限流了-您的访问过于频繁，请稍后重试";
        } else if (e instanceof DegradeException){
            result = "接口被降级了-调用服务响应异常,已进行降级";
        }  else if (e instanceof ParamFlowException){
            result = "热点参数异常-您对热点参数访问过于频繁，请稍后重试";
        } else if (e instanceof AuthorityException){
            result = "授权异常";
        }else if (e instanceof SystemBlockException){
            result = "系统负载异常";
        }
        response.getWriter().write(result);
    }
}
