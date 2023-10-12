package uk.co.listeningear.checkin;

import java.math.BigDecimal;

public interface SessionOperation {

    void execute(BigDecimal id) throws SessionOperationException;

}
