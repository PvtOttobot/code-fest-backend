package uk.co.listeningear.checkin;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AlertSearchOperation {

    private final AlertRepository alertRepository;

    public AlertSearchOperation(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<Alert> execute(List<BigDecimal> sessionIds) {
        if (sessionIds.isEmpty())
            return alertRepository.findAll();

        return alertRepository.findAll(new AlertSearchSpecification(sessionIds));
    }

}
