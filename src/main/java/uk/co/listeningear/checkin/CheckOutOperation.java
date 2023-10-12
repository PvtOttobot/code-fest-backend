package uk.co.listeningear.checkin;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component
public class CheckOutOperation {

    private final SessionRepository sessionRepository;
    private final UpdatePusher updatePusher;

    public CheckOutOperation(SessionRepository sessionRepository, UpdatePusher updatePusher) {
        this.sessionRepository = sessionRepository;
        this.updatePusher = updatePusher;
    }


    // @Override
    public void execute(BigDecimal id) throws SessionOperationException {
        Session session = sessionRepository.getReferenceById(id);
        session.setStatus(Session.Status.COMPLETED);
        session.setEndedAt(OffsetDateTime.now());
        sessionRepository.save(session);
        updatePusher.notifyUpdates(session.getAdminId());
    }

}
