package uk.co.listeningear.checkin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;

@EnableJpaRepositories
public interface AlertRepository extends JpaRepository<Alert, BigDecimal>, JpaSpecificationExecutor<Alert> {
}
