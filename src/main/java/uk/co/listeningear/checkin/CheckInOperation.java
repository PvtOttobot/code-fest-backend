package uk.co.listeningear.checkin;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component
public class CheckInOperation {

    private final SessionRepository sessionRepository;
    private final CheckAndAlertTaskScheduler checkAndAlertTaskScheduler;
    private final UpdatePusher updatePusher;

    public CheckInOperation(SessionRepository sessionRepository, CheckAndAlertTaskScheduler checkAndAlertTaskScheduler,
                            UpdatePusher updatePusher) {
        this.sessionRepository = sessionRepository;
        this.checkAndAlertTaskScheduler = checkAndAlertTaskScheduler;
        this.updatePusher = updatePusher;
    }

    // @Override
    public void execute(BigDecimal id) throws SessionOperationException {
        Session session = sessionRepository.getReferenceById(id);
        session.setStatus(Session.Status.IN_PROGRESS);
        session.setStartedAt(OffsetDateTime.now());
        sessionRepository.save(session);
        updatePusher.notifyUpdates(session.getAdminId());

        checkAndAlertTaskScheduler.scheduleForId(id, session.getExpectedEnd());
    }

}
