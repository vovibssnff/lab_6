package serv.log;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogFilter implements Filter {

    @Override
    public boolean isLoggable(LogRecord log) {

        return log.getLevel() != Level.CONFIG;
    }

}