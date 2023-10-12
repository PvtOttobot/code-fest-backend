package uk.co.listeningear.checkin;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class CheckAndAlarmTaskScheduler {

    private final SessionRepository sessionRepository;
    private final AlarmOperation alarmOperation;
    private final TaskScheduler checkAndAlarmTaskScheduler;

    public CheckAndAlarmTaskScheduler(SessionRepository sessionRepository,
                                      AlarmOperation alarmOperation,
                                      TaskScheduler checkAndAlarmTaskScheduler) {

        this.sessionRepository = sessionRepository;
        this.alarmOperation = alarmOperation;
        this.checkAndAlarmTaskScheduler = checkAndAlarmTaskScheduler;
    }

    public void scheduleForId(BigDecimal id, OffsetDateTime triggerTime) {
        checkAndAlarmTaskScheduler.schedule(new CheckAndAlarmTask(id),
                                            triggerTime.toInstant());
    }

    private class CheckAndAlarmTask implements Runnable {

        private final BigDecimal sessionId;

        private CheckAndAlarmTask(BigDecimal sessionId) {
            this.sessionId = sessionId;
        }

        @Override
        public void run() {
            Session session = sessionRepository.getReferenceById(sessionId);

            if (session.getStatus().equals("inactive"))
                return;

            if (session.getExpectedEnd().isAfter(OffsetDateTime.now())) {
                scheduleForId(sessionId, session.getExpectedEnd());
            }

            // TODO: buffer time?
            try {
                alarmOperation.execute(sessionId);
            } catch (SessionOperationException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
