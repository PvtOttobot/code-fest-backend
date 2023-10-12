package uk.co.listeningear.checkin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/alerts")
public class AlertController {

    private final AlertSearchOperation alertSearchOperation;

    public AlertController(AlertSearchOperation alertSearchOperation) {
        this.alertSearchOperation = alertSearchOperation;
    }

    @GetMapping
    public ResponseEntity<List<Alert>> getAlerts(@RequestParam(required = false, defaultValue = "") List<BigDecimal> sessionIds) {
        return ResponseEntity.ok(alertSearchOperation.execute(sessionIds));
    }

}
