package serv;

import serv.managment.ServerConnector;
import serv.managment.ServerState;

public class ServerLauncher {
    public static void main(String[] args) {
        //ServerState.setPort(Integer.parseInt(args[0]));
        ServerConnector.init();
    }
}
