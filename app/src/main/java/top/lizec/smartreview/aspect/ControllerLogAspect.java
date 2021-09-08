package top.lizec.smartreview.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import top.lizec.smartreview.utils.LogFormatUtils;

@Aspect
@Profile("dev")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
@Slf4j
public class ControllerLogAspect {

    @Pointcut("execution(* top.lizec.smartreview.web..*.*(..))")
    public void controllerLog() {

    }

    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        // 去掉方法前面上的括号
        methodName = methodName.substring(0, methodName.indexOf('('));
        log.info("收到Web请求: {}({})", methodName, LogFormatUtils.argToString(joinPoint.getArgs()));
    }

}
