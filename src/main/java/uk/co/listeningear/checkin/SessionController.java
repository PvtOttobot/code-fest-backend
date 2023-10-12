package uk.co.listeningear.checkin;

import org.springframework.stereotype.Controller;

@Controller
public class SessionController {

    private final CheckInOperation checkInOperation;
    private final CheckOutOperation checkOutOperation;
    private final ExtendOperation extendOperation;
    private final AlarmOperation alarmOperation;

    public SessionController(CheckInOperation checkInOperation,
                             CheckOutOperation checkOutOperation,
                             ExtendOperation extendOperation,
                             AlarmOperation alarmOperation) {
        this.checkInOperation = checkInOperation;
        this.checkOutOperation = checkOutOperation;
        this.extendOperation = extendOperation;
        this.alarmOperation = alarmOperation;
    }
}
