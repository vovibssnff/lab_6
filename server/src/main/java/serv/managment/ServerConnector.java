package serv.managment;

import cmn.cmd.Command;
import serv.ServerConnectionService;
import cmn.OutputEngine;
import serv.load.Serializer;
import serv.log.Logging;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Лончер, запускающий основной сканер, который считывает команды и аргументы в двух режимах: из файла и в формате обычного ввода через консоль
 */
public class ServerConnector {
    private static final CollectionReceiver receiver = new CollectionReceiver();
    public static String resp = null;
    public static Logger logger;
    public static void init() {
        Serializer.parse();
        CollectionService.sortCollection();
        logger = Logging.logger;

        ServerState.setTmpFile(new File("temporary.tmp"));
        ServerState.setCollectionReceiver(new CollectionReceiver());
        System.out.println(OutputEngine.greeting_msg());
        ServerConnectionService serverConnectionService = new ServerConnectionService();
        serverConnectionService.initConnection(ServerState.getPort());
    }
    public static void commandExecute(Command currentCommand, File tmpFile) {
        currentCommand.execute();

    }
}
