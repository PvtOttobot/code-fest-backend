package uk.co.listeningear.checkin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;

@EnableJpaRepositories
public interface SessionRepository extends JpaRepository<Session, BigDecimal> {
}
