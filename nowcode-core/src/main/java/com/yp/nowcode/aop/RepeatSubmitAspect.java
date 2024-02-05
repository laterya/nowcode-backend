package com.yp.nowcode.aop;

import com.yp.nowcode.annotation.RepeatSubmit;
import com.yp.nowcode.common.ErrorCode;
import com.yp.nowcode.exception.BusinessException;
import com.yp.nowcode.model.dto.chart.GenChartByAiRequest;
import com.yp.nowcode.model.entity.User;
import com.yp.nowcode.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Aspect
public class RepeatSubmitAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private UserService userService;

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.yp.nowcode.annotation.RepeatSubmit)")
    public void repeatSubmit() {
    }

    @Around("repeatSubmit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
        if (annotation == null) {
            return joinPoint.proceed();
        }
        User loginUser = userService.getLoginUser(request);
        Long id = loginUser.getId();
        String redisKey = "repeat_submit_key:" + id.toString();
        Object[] args = joinPoint.getArgs();
        GenChartByAiRequest genChartByAiRequest = (GenChartByAiRequest) args[1];
        redisKey += genChartByAiRequest.getName() + ":" + genChartByAiRequest.getChartType() + ":" + genChartByAiRequest.getGoal();


        Boolean b = redisTemplate.opsForValue().setIfAbsent(redisKey, redisKey, annotation.expireTime(), TimeUnit.SECONDS);
        if (Boolean.FALSE.equals(b)) {
            throw new BusinessException(ErrorCode.OPERATION_REPEAT_ERROR, "请勿重复提交请求");
        }
        return joinPoint.proceed();
    }
}

