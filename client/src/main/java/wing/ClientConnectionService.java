package wing;

import wing.data.Transmitter;
import wing.managment.ProgramState;

import java.io.IOException;
import java.net.*;


public class ClientConnectionService {
    private static InetSocketAddress serverAddress;
    private static DatagramSocket socket = null;
    public static void initConnection() {

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        serverAddress = new InetSocketAddress("localhost", 2222);
    }
    public static String sendRequest(Transmitter message) {
        String jsonRequest = ProgramState.getGson().toJson(message);

        byte[] sendData = jsonRequest.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress);
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            socket.receive(receivePacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String jsonResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
        return ProgramState.getGson().fromJson(jsonResponse, String.class);

    }

}