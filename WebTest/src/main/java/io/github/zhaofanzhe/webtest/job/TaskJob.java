package io.github.zhaofanzhe.webtest.job;

import io.github.zhaofanzhe.webtest.model.Account;
import io.github.zhaofanzhe.webtest.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class TaskJob implements Job {

    private final AccountService accountService;

    @Override
    @Transactional
    public void execute(JobExecutionContext context) throws JobExecutionException {
        final JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        final String code = dataMap.getString("code");
        log.info("任务执行了,code 是 {}", code);

        final Account account = Account.builder()
                .phone(code)
                .build();

        accountService.save(account);

        final char first = code.charAt(0);

        switch (first) {
            case '0', '1', '2', '3', '4' -> {
                log.info("回滚了");
                throw new RuntimeException("回滚了");
            }
            default -> {
                log.info("通过了");
            }
        }

    }

}
