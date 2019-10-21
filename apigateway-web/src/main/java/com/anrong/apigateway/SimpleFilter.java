package com.anrong.apigateway;

import com.anrong.apigateway.cache.CacheKit;
import com.anrong.apigateway.util.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class SimpleFilter extends ZuulFilter {


    private static final Logger log = LoggerFactory.getLogger(ZuulFilter.class);

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURL().toString();
        //此处判断是否要拦截**************
        //过滤登录方法
        if (url.contains("/urpm-wx/login")) {
            return null;
        }

        //先从 cookie 中取 token，cookie 中取失败再从 header 中取，两重校验
        //通过工具类从 Cookie 中取出 token
        Cookie tokenCookie = CookieUtil.getCookie(request, "token");
        if (tokenCookie == null || StringUtils.isEmpty(tokenCookie.getValue())) {
            readTokenFromHeader(ctx, request);
        } else {
           return verifyToken(ctx, request, tokenCookie.getValue());
        }


        //*******************开始拦截****************************
        log.info(String.format("%s  拦截的url: %s", request.getMethod(), url));
        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 从 header 中读取 token 并校验
     */
    private Object readTokenFromHeader(RequestContext requestContext, HttpServletRequest request) {
        //从 header 中读取
        String headerToken = request.getHeader("token");
        if (StringUtils.isEmpty(headerToken)) {
            setUnauthorizedResponse(requestContext);
        } else {
           return verifyToken(requestContext, request, headerToken);
        }
        return null;
    }

    /**
     * 从Redis中校验token
     */
    /*private Object verifyToken(RequestContext requestContext, HttpServletRequest request, String token) {
        //需要从cookie或header 中取出 userId 来校验 token 的有效性，因为每个用户对应一个token，在Redis中是以 TOKEN_userId 为键的
        Cookie userIdCookie = CookieUtil.getCookie(request, "userId");
        if (userIdCookie == null || StringUtils.isEmpty(userIdCookie.getValue())) {
            //从header中取userId
            String userId = request.getHeader("userId");
            if (StringUtils.isEmpty(userId)) {
                setUnauthorizedResponse(requestContext);
            } else {
                String redisToken = (String) redisService.get(userId);
                if (StringUtils.isEmpty(redisToken) || !redisToken.equals(token)) {
                    setUnauthorizedResponse(requestContext);
                }else {
                    Long expire = redisService.getExpire(userId);
                    if (expire != null && expire < 300){
                    redisTemplate.expire(userId,3600, TimeUnit.SECONDS);
                    }
                    return null;
                }
            }
        } else {
            String redisToken = (String) redisService.get(userIdCookie.getValue());
            if (StringUtils.isEmpty(redisToken) || !redisToken.equals(token)) {
                setUnauthorizedResponse(requestContext);
            }
        }
        return null;
    }*/
    private Object verifyToken(RequestContext requestContext, HttpServletRequest request, String token) {
        //需要从cookie或header 中取出 userId 来校验 token 的有效性，因为每个用户对应一个token，在Redis中是以 TOKEN_userId 为键的
        Cookie userIdCookie = CookieUtil.getCookie(request, "userId");
        if (userIdCookie == null || StringUtils.isEmpty(userIdCookie.getValue())) {
            //从header中取userId
            String userId = request.getHeader("userId");
            if (StringUtils.isEmpty(userId)) {
                setUnauthorizedResponse(requestContext);
            } else {
                String jwt = CacheKit.get("jwt", userId);
                if (StringUtils.isEmpty(jwt) || !token.equals(jwt)) {
                    setUnauthorizedResponse(requestContext);
                } else {
                    return null;
                }
            }
        } else {
            String jwt = CacheKit.get("jwt", userIdCookie);
            if (StringUtils.isEmpty(jwt) || !token.equals(jwt)) {
                setUnauthorizedResponse(requestContext);
            }
        }
        return null;
    }
    /**
     * 设置 401 无权限状态
     */
    private void setUnauthorizedResponse(RequestContext ctx) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        ctx.setResponseBody("{\"code\":401,\"msg\":\"没有访问权限！\"}");
        ctx.getResponse().setContentType("text/html;charset=UTF-8");
    }
}
