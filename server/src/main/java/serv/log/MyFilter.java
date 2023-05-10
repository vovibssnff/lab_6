package serv.log;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MyFilter implements Filter {

    @Override
    public boolean isLoggable(LogRecord log) {

        if(log.getLevel() == Level.CONFIG) return false;
        return true;
    }

}