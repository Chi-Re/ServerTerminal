package server.terminal.util.exception;

import server.terminal.ChireCore;

import java.io.PrintWriter;
import java.io.StringWriter;

public class SeverRuntimeException extends RuntimeException {

    public SeverRuntimeException(String message) {
        super(message);
    }

    public SeverRuntimeException(String message, Throwable cause) {
        super(message, cause);
        ChireCore.log.err(message, cause);
    }

    public SeverRuntimeException(Throwable cause){
        super(cause);
    }
}
