package uk.co.listeningear.checkin;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExtendOperation {

    private final SessionRepository sessionRepository;
    private final UpdatePusher updatePusher;

    public ExtendOperation(SessionRepository sessionRepository, UpdatePusher updatePusher) {
        this.sessionRepository = sessionRepository;
        this.updatePusher = updatePusher;
    }

    public void execute(BigDecimal id, long minutesToExtend) throws SessionOperationException {
        Session session = sessionRepository.getReferenceById(id);
        session.setEndedAt(session.getExpectedEnd().plusMinutes(minutesToExtend));
        sessionRepository.save(session);
        updatePusher.notifyUpdates(session.getAdminId());
    }

}
