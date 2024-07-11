package org.squirrel.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author luobaosong
 * @date 2024-06-27 15:39
 */

@Configuration
public class ScheduleConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 设置线程池大小
        taskScheduler.setPoolSize(5);
        taskScheduler.setThreadNamePrefix("TaskScheduler-");
        taskScheduler.initialize();
        return taskScheduler;
    }
}

