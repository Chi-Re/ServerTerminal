package server.terminal.util.exception;

import server.terminal.io.FileMod;

import static server.terminal.ChireCore.log;
import static server.terminal.ChireCore.setting;

public class ModRuntimeException extends RuntimeException {
    public ModRuntimeException(String message) {
        super(message);
        log.err(message);
    }

    public ModRuntimeException(FileMod mod, String message, Throwable cause){
        super(message, cause);
        this.modLog(mod, message, cause);
    }

    public ModRuntimeException(String message, Throwable cause){
        super(message, cause);
        this.modLog(null, message, cause);
    }

    public void modLog(FileMod mod, String message, Throwable cause){
        log.print(message);
        String time = setting.getTime();
        String modName;
        if (mod != null){
            modName = mod.name;
        } else {
            modName = "-MOD-";
        }
        log.log("[E] ["+modName+"] ["+time+"] "+message+" "+log.getError(cause), false, true);
    }
}
