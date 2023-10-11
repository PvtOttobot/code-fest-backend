package uk.co.listeningear.checkin;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CheckAndAlarmTaskScheduler {

    private final SessionRepository sessionRepository;
    private final SessionOperation alarmOperation;
    private final ScheduledExecutorService checkAndAlarmTaskScheduler;

    public CheckAndAlarmTaskScheduler(SessionRepository sessionRepository,
                                      SessionOperation alarmOperation,
                                      ScheduledExecutorService checkAndAlarmTaskScheduler) {

        this.sessionRepository = sessionRepository;
        this.alarmOperation = alarmOperation;
        this.checkAndAlarmTaskScheduler = checkAndAlarmTaskScheduler;
    }

    public void scheduleForId(BigDecimal id, OffsetDateTime triggerTime) {
        checkAndAlarmTaskScheduler.schedule(new CheckAndAlarmTask(id),
                                            ChronoUnit.MINUTES.between(OffsetDateTime.now(), triggerTime),
                                            TimeUnit.MINUTES);
    }

    class CheckAndAlarmTask implements Runnable {

        private final BigDecimal sessionId;

        CheckAndAlarmTask(BigDecimal sessionId) {
            this.sessionId = sessionId;
        }

        @Override
        public void run() {
            Session session = sessionRepository.getReferenceById(sessionId);
            if (session.getStatus().equals("active") && session.getExpectedEnd().isBefore(OffsetDateTime.now())) {
                try {
                    alarmOperation.execute(sessionId);
                } catch (SessionOperationException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

}
