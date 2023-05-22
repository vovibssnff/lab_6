package cl.managment;

import cl.ClientConnectionService;
import cmn.service.Transmitter;

public class RequestService {
    public static void sendRequest(Transmitter transmitter) {
        String resp = ClientConnectionService.sendRequest(transmitter);
        String res = ProgramState.getGson().fromJson(resp, String.class);
        System.out.println(res);
    }
}
