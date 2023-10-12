package uk.co.listeningear.checkin;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public class AlertSearchSpecification implements Specification<Alert> {

    private final List<BigDecimal> sessionIds;

    public AlertSearchSpecification(List<BigDecimal> sessionIds) {
        this.sessionIds = sessionIds;
    }

    @Override
    public Predicate toPredicate(Root<Alert> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return root.get("session").get("id").in(sessionIds);
    }
}
