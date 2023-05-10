package serv.log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Logging {

    public static Logger logger;

    static  {
        logger = Logger.getLogger("ServerLogger");

        logger.setLevel(Level.FINE);
        //logger.addHandler(new ConsoleHandler());
        //logger.addHandler(new MyHandler());
        try {
            //FileHandler file name with max size and number of log files limit
            Handler fileHandler = new FileHandler("logger.log");
            fileHandler.setFormatter(new MyFormatter());
            //setting custom filter for FileHandler
            fileHandler.setFilter(new MyFilter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // To remove the console handler, use

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

}