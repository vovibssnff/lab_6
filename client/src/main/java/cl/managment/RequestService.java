package cl.managment;

import cl.ClientConnectionService;
import cmn.data.Transmitter;

public class RequestService {
    public static void sendRequest(Transmitter transmitter) {
        String resp = ClientConnectionService.sendRequest(transmitter);
        if (resp!=null) {
            System.out.println(resp);
        }
    }
}
