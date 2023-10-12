package uk.co.listeningear.checkin;

import java.math.BigDecimal;

public interface UpdatePusher {

    void notifyUpdates(BigDecimal userId);

}
