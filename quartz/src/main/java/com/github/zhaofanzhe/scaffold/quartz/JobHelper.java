package com.github.zhaofanzhe.scaffold.quartz;

import com.github.zhaofanzhe.scaffold.toolkit.SpringContextHolder;
import org.quartz.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class JobHelper {

    /**
     * 启动 Job
     *
     * @param clazz      Job类
     * @param name       名称
     * @param group      组
     * @param time       触发时间
     * @param jobDataMap 数据
     * @throws SchedulerException SchedulerException
     */
    public static <T extends Job> void startJob(Class<T> clazz, String name, String group, LocalDateTime time, JobDataMap jobDataMap) throws SchedulerException {
        startJob(clazz, name, group, time, jobDataMap, false);
    }

    /**
     * 启动 Job
     *
     * @param clazz      Job类
     * @param name       名称
     * @param group      组
     * @param time       触发时间
     * @param jobDataMap 数据
     * @param update     如果存在指定的key(name,group),则更新任务
     * @throws SchedulerException SchedulerException
     */
    public static <T extends Job> void startJob(Class<T> clazz, String name, String group, LocalDateTime time, JobDataMap jobDataMap, boolean update) throws SchedulerException {

        final Scheduler scheduler = Objects.requireNonNull(SpringContextHolder.getBean(Scheduler.class));

        if (update) {
            final JobKey jobKey = JobKey.jobKey(name, group);
            if (scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
            }
        }

        JobBuilder jobBuilder = JobBuilder.newJob(clazz)
                .withIdentity(name, group);

        if (jobDataMap != null) {
            jobBuilder = jobBuilder.setJobData(jobDataMap);
        }

        final JobDetail jobDetail = jobBuilder
                .build();

        final Date startAt = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());

        final Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity(name, group)
                .withSchedule(
                        SimpleScheduleBuilder
                                .simpleSchedule()
                                .withMisfireHandlingInstructionFireNow()
                )
                .startAt(startAt)
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 删除任务
     * @param name 名称
     * @param group 组
     * @throws SchedulerException SchedulerException
     */
    public static void deleteJob(String name, String group) throws SchedulerException {
        final Scheduler scheduler = Objects.requireNonNull(SpringContextHolder.getBean(Scheduler.class));
        scheduler.deleteJob(JobKey.jobKey(name, group));
    }

}
