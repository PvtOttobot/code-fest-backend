package uk.co.listeningear.checkin;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class SessionSearchSpecification implements Specification<Session> {

    private final LocalDate date;
    private final BigDecimal therapistId;
    private final BigDecimal adminId;

    public SessionSearchSpecification(LocalDate date, BigDecimal therapistId, BigDecimal adminId) {
        this.date = date;
        this.therapistId = therapistId;
        this.adminId = adminId;
    }

    @Override
    public Predicate toPredicate(Root<Session> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (date != null) {
            OffsetDateTime startOfDay = date.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("expectedStart"), startOfDay));

            OffsetDateTime endOfDay = date.plusDays(1).atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();
            predicates.add(criteriaBuilder.lessThan(root.get("expectedStart"), endOfDay));
        }

        if (therapistId != null) {
            predicates.add(criteriaBuilder.equal(root.get("therapist").get("id"), therapistId));
        }

        if (adminId != null) {
            predicates.add(criteriaBuilder.equal(root.get("admin").get("id"), adminId));
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

}
