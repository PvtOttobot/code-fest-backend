package uk.co.listeningear.checkin;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component
@Transactional
public class CheckAndAlertOperation {

    private final AlertOperation alertOperation;
    private final SessionRepository sessionRepository;

    public CheckAndAlertOperation(AlertOperation alertOperation, SessionRepository sessionRepository) {
        this.alertOperation = alertOperation;
        this.sessionRepository = sessionRepository;
    }

    public void execute(BigDecimal id, CheckAndAlertTaskScheduler scheduler) {
        Session session = sessionRepository.getReferenceById(id);

        if (session.getStatus() != Session.Status.IN_PROGRESS)
            return;

        if (session.getExpectedEnd().isAfter(OffsetDateTime.now())) {
            scheduler.scheduleForId(id, session.getExpectedEnd());
        }

        try {
            alertOperation.execute(id);
        } catch (SessionOperationException e) {
            throw new RuntimeException(e);
        }
    }

}
