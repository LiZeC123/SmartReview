package top.lizec.smartreview.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ServerLogAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());

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
        String methodName = joinPoint.getSignature().toShortString();
        logger.info(String.format("执行方法 %s 参数为 %s", methodName, Arrays.toString(joinPoint.getArgs())));
    }

    @AfterReturning(returning = "ret", pointcut = "serviceLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        logger.info(String.format("返回结果 %s (用时 %d ms)", ret, System.currentTimeMillis() - startTime.get()));
    }
}