package top.lizec.smartreview.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import top.lizec.smartreview.aspect.ServiceLog;

@Component
public class SimpleUpdateTimeTask {

    @ServiceLog("更新复习参数间任务")
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateParameter() {
        System.out.println("Task For Hour 1");
    }

}
