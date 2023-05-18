package serv;

import serv.managment.ServerConnector;
import serv.managment.ServerController;
import serv.managment.ServerState;

public class ServerLauncher {
    public static void main(String[] args) {
        //ServerState.setPort(Integer.parseInt(args[0]));
        ServerController controller = new ServerController();
        controller.start();
        ServerConnector.init();
    }
}
