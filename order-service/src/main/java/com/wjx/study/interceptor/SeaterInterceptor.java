package com.wjx.study.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnClass({RequestInterceptor.class, GlobalTransactional.class})
public class SeaterInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
         String xid = RootContext.getXID();
         if(StringUtils.isNotBlank(xid)){
             requestTemplate.header(RootContext.KEY_XID,xid);
         }
    }
}
