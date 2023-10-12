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
        if (minutesToExtend <= 0)
            throw new SessionOperationException("Tried to extend by non-positive value: " + minutesToExtend);

        Session session = sessionRepository.getReferenceById(id);
        if (session.getStatus() != Session.Status.IN_PROGRESS)
            throw new SessionOperationException("Invalid status: " + session.getStatus());

        session.setEndedAt(session.getExpectedEnd().plusMinutes(minutesToExtend));
        sessionRepository.save(session);
        updatePusher.notifyUpdates(session.getAdminId());
    }

}
