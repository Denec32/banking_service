package com.denec.correctionservice.scheduler;

import com.denec.correctionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {
    private final TransactionService transactionService;

    @Scheduled(fixedRateString = "${scheduler.delay}")
    public void scheduleResendTransactions() {
        transactionService.resendTransactions();
    }
}
