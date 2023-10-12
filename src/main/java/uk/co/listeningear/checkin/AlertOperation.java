package uk.co.listeningear.checkin;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AlertOperation {

    private final AlertRepository alertRepository;
    private final SessionRepository sessionRepository;
    private final UpdatePusher updatePusher;

    public AlertOperation(AlertRepository alertRepository,
                          SessionRepository sessionRepository,
                          UpdatePusher updatePusher) {
        this.alertRepository = alertRepository;
        this.sessionRepository = sessionRepository;
        this.updatePusher = updatePusher;
    }

    // @Override
    public void execute(BigDecimal id) throws SessionOperationException {
        Session session = sessionRepository.getReferenceById(id);
        Alert alert = new Alert(session);
        alertRepository.save(alert);
        updatePusher.notifyUpdates(session.getAdmin().getId());
    }

}
