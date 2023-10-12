package uk.co.listeningear.checkin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequestMapping("/session")
public class SessionController {

    private final CheckInOperation checkInOperation;
    private final CheckOutOperation checkOutOperation;
    private final ExtendOperation extendOperation;
    private final AlertOperation alertOperation;

    public SessionController(CheckInOperation checkInOperation,
                             CheckOutOperation checkOutOperation,
                             ExtendOperation extendOperation,
                             AlertOperation alertOperation) {
        this.checkInOperation = checkInOperation;
        this.checkOutOperation = checkOutOperation;
        this.extendOperation = extendOperation;
        this.alertOperation = alertOperation;
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
    public ResponseEntity<Void> extend(@PathVariable BigDecimal id, long minutes) throws SessionOperationException {
        extendOperation.execute(id, minutes);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/alert")
    public ResponseEntity<Void> alert(@PathVariable BigDecimal id) throws SessionOperationException {
        alertOperation.execute(id);
        return ResponseEntity.noContent().build();
    }

}
