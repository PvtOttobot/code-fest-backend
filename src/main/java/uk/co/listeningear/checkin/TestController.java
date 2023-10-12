package uk.co.listeningear.checkin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Controller
public class TestController {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public TestController(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/createtest")
    public ResponseEntity<Void> createTest() {
        User admin = userRepository.getReferenceById(BigDecimal.valueOf(1));
        User therapist = userRepository.getReferenceById(BigDecimal.valueOf(2));

        Session session = new Session();
        session.setAdmin(admin);
        session.setTherapist(therapist);
        session.setExpectedStart(OffsetDateTime.now().plusSeconds(30));
        session.setExpectedEnd(OffsetDateTime.now().plusSeconds(30));
        session.setStatus(Session.Status.SCHEDULED);
        sessionRepository.save(session);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/search")
    public ResponseEntity<String> searchTest(@RequestParam(required = false) LocalDate date,
                                             @RequestParam(required = false) BigDecimal therapistId,
                                             @RequestParam(required = false) BigDecimal adminId) {
        SessionSearchSpecification spec = new SessionSearchSpecification(date, therapistId, adminId);
        List<Session> sessions = sessionRepository.findAll(spec);
        return ResponseEntity.ok(sessions.toString());
    }

}
