package uk.co.listeningear.checkin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component
public class CheckAndAlarmTaskScheduler {

    private final SessionRepository sessionRepository;
    private final AlarmOperation alarmOperation;
    private final TaskScheduler checkAndAlarmTaskScheduler;
    private final long minutesAlarmBuffer;

    public CheckAndAlarmTaskScheduler(SessionRepository sessionRepository,
                                      AlarmOperation alarmOperation,
                                      TaskScheduler checkAndAlarmTaskScheduler,
                                      @Value("${alarm.buffer.mins:0}") long minutesAlarmBuffer) {

        this.sessionRepository = sessionRepository;
        this.alarmOperation = alarmOperation;
        this.checkAndAlarmTaskScheduler = checkAndAlarmTaskScheduler;
        this.minutesAlarmBuffer = minutesAlarmBuffer;
    }

    public void scheduleForId(BigDecimal id, OffsetDateTime triggerTime) {
        checkAndAlarmTaskScheduler.schedule(new CheckAndAlarmTask(id),
                                            triggerTime.plusMinutes(minutesAlarmBuffer).toInstant());
    }

    private class CheckAndAlarmTask implements Runnable {

        private final BigDecimal sessionId;

        private CheckAndAlarmTask(BigDecimal sessionId) {
            this.sessionId = sessionId;
        }

        @Override
        public void run() {
            Session session = sessionRepository.getReferenceById(sessionId);

            if (session.getStatus() != Session.Status.IN_PROGRESS)
                return;

            if (session.getExpectedEnd().isAfter(OffsetDateTime.now())) {
                scheduleForId(sessionId, session.getExpectedEnd());
            }

            try {
                alarmOperation.execute(sessionId);
            } catch (SessionOperationException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
