package uk.co.listeningear.checkin;

import org.springframework.stereotype.Controller;

@Controller
public class SessionController {

    private final SessionOperation checkInOperation;
    private final SessionOperation checkOutOperation;
    private final SessionOperation extendOperation;
    private final SessionOperation alarmOperation;

    public SessionController(SessionOperation checkInOperation,
                             SessionOperation checkOutOperation,
                             SessionOperation extendOperation,
                             SessionOperation alarmOperation) {
        this.checkInOperation = checkInOperation;
        this.checkOutOperation = checkOutOperation;
        this.extendOperation = extendOperation;
        this.alarmOperation = alarmOperation;
    }
}
