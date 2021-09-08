package top.lizec.smartreview.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.lizec.smartreview.utils.LogFormatUtils;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
@Slf4j
public class ServerLogAspect {
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(* top.lizec.smartreview.service..*.*(..))")
    public void serviceDefaultLog() {
    }

    @Pointcut("@annotation(top.lizec.smartreview.aspect.ServiceLog)")
    public void serviceAnnotationLog() {
    }

    @Pointcut("serviceDefaultLog() || serviceAnnotationLog()")
    public void serviceLog() {
    }

    @Before("serviceLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        String methodName = getMethodName(joinPoint);
        log.info("开始服务: {}({})", methodName, LogFormatUtils.argToString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "serviceLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        // 处理完请求，返回内容
        String methodName = getMethodName(joinPoint);
        long elapsedTime = System.currentTimeMillis() - startTime.get();
        String returnValue;
        if (ret instanceof List && ((List<?>) ret).size() > 6) {
            List<?> result = (List<?>) ret;
            int size = result.size();
            returnValue = String.format("<== [%s %s %s ... %s %s %s]", result.get(0), result.get(1), result.get(2),
                    result.get(size - 3), result.get(size - 2), result.get(size - 1));
            log.info("结束服务: {}(用时 {} ms) ==> {}", methodName, elapsedTime, returnValue);
        } else {
            log.info("结束服务: {}(用时 {} ms) ==> {}", methodName, elapsedTime, ret);
        }
    }

    private String getMethodName(JoinPoint joinPoint) {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        String methodName = method.toShortString();
        methodName = methodName.substring(0, methodName.indexOf('('));


        // 有用户给定的名字, 就用用户给定的名字, 否则用默认的函数名
        return Optional.ofNullable(method.getMethod().getAnnotation(ServiceLog.class))
                .map(ServiceLog::value).orElse(methodName);
    }
}