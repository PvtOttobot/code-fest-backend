package uk.co.listeningear.checkin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/sessions")
public class SessionController {

    private final CheckInOperation checkInOperation;
    private final CheckOutOperation checkOutOperation;
    private final ExtendOperation extendOperation;
    private final AlertOperation alertOperation;
    private final SessionSearchOperation sessionSearchOperation;

    public SessionController(CheckInOperation checkInOperation,
                             CheckOutOperation checkOutOperation,
                             ExtendOperation extendOperation,
                             AlertOperation alertOperation,
                             SessionSearchOperation sessionSearchOperation) {
        this.checkInOperation = checkInOperation;
        this.checkOutOperation = checkOutOperation;
        this.extendOperation = extendOperation;
        this.alertOperation = alertOperation;
        this.sessionSearchOperation = sessionSearchOperation;
    }

    @GetMapping
    public ResponseEntity<List<Session>> getSessions(@RequestParam(required = false) LocalDate date,
                                                     @RequestParam(required = false) BigDecimal therapistId,
                                                     @RequestParam(required = false) BigDecimal adminId)
            throws SessionOperationException {
        return ResponseEntity.ok(sessionSearchOperation.execute(date, therapistId, adminId));
    }

    @PostMapping(value = "/{id}/checkin")
    public ResponseEntity<Void> checkIn(@PathVariable BigDecimal id) throws SessionOperationException {
        checkInOperation.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/checkout")
    public ResponseEntity<Void> checkOut(@PathVariable BigDecimal id) throws SessionOperationException {
        checkOutOperation.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/extend/{minutes}")
    public ResponseEntity<Void> extend(@PathVariable BigDecimal id, @PathVariable long minutes) throws SessionOperationException {
        extendOperation.execute(id, minutes);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/alert")
    public ResponseEntity<Void> alert(@PathVariable BigDecimal id) throws SessionOperationException {
        alertOperation.execute(id);
        return ResponseEntity.noContent().build();
    }

}
