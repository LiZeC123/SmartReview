package top.lizec.smartreview.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Profile("dev")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class ControllerLogAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* top.lizec.smartreview.web..*.*(..))")
    public void controllerLog() {

    }

    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Web请求: " + methodName);
        logger.info("==> " + Arrays.toString(joinPoint.getArgs()));
    }

}
