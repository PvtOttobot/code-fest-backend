package uk.co.listeningear.checkin;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class SessionSearchOperation {

    private final SessionRepository sessionRepository;

    public SessionSearchOperation(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> execute(LocalDate date, BigDecimal therapistId, BigDecimal adminId) throws SessionOperationException {
        return sessionRepository.findAll(new SessionSearchSpecification(date, therapistId, adminId));
    }

}
