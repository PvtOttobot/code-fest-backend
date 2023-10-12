package uk.co.listeningear.checkin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component
public class CheckAndAlertTaskScheduler {

    private final CheckAndAlertOperation checkAndAlertOperation;
    private final TaskScheduler checkAndAlarmTaskScheduler;
    private final long minutesAlarmBuffer;

    public CheckAndAlertTaskScheduler(CheckAndAlertOperation checkAndAlertOperation,
                                      TaskScheduler checkAndAlarmTaskScheduler,
                                      @Value("${alarm.buffer.mins:0}") long minutesAlarmBuffer) {

        this.checkAndAlertOperation = checkAndAlertOperation;
        this.checkAndAlarmTaskScheduler = checkAndAlarmTaskScheduler;
        this.minutesAlarmBuffer = minutesAlarmBuffer;
    }

    public void scheduleForId(BigDecimal id, OffsetDateTime triggerTime) {
        checkAndAlarmTaskScheduler.schedule(() -> checkAndAlertOperation.execute(id, this),
                                            triggerTime.plusMinutes(minutesAlarmBuffer).toInstant());
    }

}
