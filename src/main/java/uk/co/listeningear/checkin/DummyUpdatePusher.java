package uk.co.listeningear.checkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DummyUpdatePusher implements UpdatePusher {

    private static final Logger LOG = LoggerFactory.getLogger(DummyUpdatePusher.class);

    @Override
    public void notifyUpdates(BigDecimal userId) {
        LOG.info("pushing update to {}", userId);
    }

}
