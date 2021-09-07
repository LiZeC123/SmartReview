package top.lizec.smartreview.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        String methodName = getMethodName(joinPoint);

        logger.info("开始服务 " + methodName);
        logger.info("==> " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "serviceLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        // 处理完请求，返回内容
        String methodName = getMethodName(joinPoint);
        logger.info(String.format("结束服务(用时 %d ms) %s", System.currentTimeMillis() - startTime.get(), methodName));
        if (ret instanceof List && ((List<?>) ret).size() > 6) {
            List<?> result = (List<?>) ret;
            int size = result.size();
            logger.info(String.format("<== [%s %s %s ... %s %s %s]",
                    result.get(0), result.get(1), result.get(2),
                    result.get(size - 3), result.get(size - 2), result.get(size - 1)));
        } else {
            logger.info(String.format("<== %s", ret));
        }
    }

    private String getMethodName(JoinPoint joinPoint) {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        String methodName = method.toShortString();

        // 有用户给定的名字, 就用用户给定的名字, 否则用默认的函数名
        return Optional.ofNullable(method.getMethod().getAnnotation(ServiceLog.class))
                .map(ServiceLog::value).orElse(methodName);
    }
}