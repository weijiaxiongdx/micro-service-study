package com.wjx.study.filter;

import com.wjx.common.constant.Constant;
import com.wjx.common.except.RRException;
import com.wjx.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Desc: 自定义全局过滤器，必须实现GlobalFilter和Ordered两个接口。用来实现统一鉴权
 * @File name：com.wjx.study.filter.AuthGlobalFilter
 * @Create on：2022/3/13 17:10
 * @Author：wjx
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisUtils redisUtils;

    // 过滤器逻辑
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 统一鉴权逻辑
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if(StringUtils.isBlank(token)){
            token = exchange.getRequest().getHeaders().getFirst("token");
        }

        if(StringUtils.isBlank(token)){
            throw new RRException(HttpStatus.UNAUTHORIZED.value(),"token不能为空" );
        }

        String key = String.format(Constant.USER_INFO_CACHE,token);
        String userInfo = redisUtils.get(key);

        if(StringUtils.isBlank(userInfo)){
            log.info("认证失败，请登录...");
            throw new RRException(HttpStatus.UNAUTHORIZED.value(),"token失效，请重新登录" );
            /*ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();*/
        }

        // 成功则放行
        return chain.filter(exchange);
    }


    // 标识当前过滤器的顺序，返回值越小，优先级越高。该值会影响全局过滤器和局部过滤器的执行顺序
    @Override
    public int getOrder() {
        return 0;
    }
}
