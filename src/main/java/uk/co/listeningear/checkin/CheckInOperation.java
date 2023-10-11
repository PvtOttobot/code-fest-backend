package uk.co.listeningear.checkin;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class CheckInOperation implements SessionOperation {

    private final SessionRepository sessionRepository;
    private final CheckAndAlarmTaskScheduler checkAndAlarmTaskScheduler;

    public CheckInOperation(SessionRepository sessionRepository, CheckAndAlarmTaskScheduler checkAndAlarmTaskScheduler) {
        this.sessionRepository = sessionRepository;
        this.checkAndAlarmTaskScheduler = checkAndAlarmTaskScheduler;
    }

    @Override
    public void execute(BigDecimal id) throws SessionOperationException {
        Session session = sessionRepository.getReferenceById(id);
        session.setStatus("active");
        session.setStartedAt(OffsetDateTime.now());
        sessionRepository.save(session);

        checkAndAlarmTaskScheduler.scheduleForId(id, session.getExpectedEnd());
    }

}
