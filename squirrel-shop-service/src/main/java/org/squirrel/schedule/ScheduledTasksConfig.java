package org.squirrel.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


/**
 * @author luobaosong
 * @date 2024-06-27 15:41
 */

@Configuration
public class ScheduledTasksConfig implements SchedulingConfigurer {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler);
    }
}

