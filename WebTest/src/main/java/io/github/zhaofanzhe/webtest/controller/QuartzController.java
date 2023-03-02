package io.github.zhaofanzhe.webtest.controller;

import io.github.zhaofanzhe.scaffold.quartz.JobHelper;
import io.github.zhaofanzhe.scaffold.toolkit.RandomCode;
import io.github.zhaofanzhe.scaffold.toolkit.Result;
import io.github.zhaofanzhe.webtest.job.TaskJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class QuartzController {

    @GetMapping("/task")
    public Result task() throws SchedulerException {
        final String code = RandomCode.nextCode(10);
        final JobDataMap dataMap = new JobDataMap();
        dataMap.put("code", code);
        JobHelper.startJob(TaskJob.class, code, "TASK", LocalDateTime.now().plusSeconds(5), dataMap);
        log.info("任务启动了,code 是 {}", code);
        return Result.success();
    }

}
