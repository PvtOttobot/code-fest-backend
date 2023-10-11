package uk.co.listeningear.checkin;

public class SessionOperationException extends Exception {
    public SessionOperationException() {
    }

    public SessionOperationException(String message) {
        super(message);
    }

    public SessionOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionOperationException(Throwable cause) {
        super(cause);
    }

    public SessionOperationException(String message,
                                     Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
