package cl;

import cmn.data.Transmitter;
import cl.managment.ProgramState;
import net.rudp.ReliableSocket;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;


public class ClientConnectionService {
    private static InetSocketAddress serverAddress;
    //private static ReliableSocket reliableSock;
    private static DatagramSocket socket = null;

    public static void initConnection() {
        try {
            socket = new DatagramSocket();
            serverAddress = new InetSocketAddress("localhost", 2222);
            //reliableSock = new ReliableSocket(socket);
            //reliableSock.connect(serverAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            socket.setSoTimeout(2000);
            socket.receive(receivePacket);

        } catch (SocketTimeoutException e) {
            System.out.println("Сервер не отвечает");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
        return ProgramState.getGson().fromJson(jsonResponse, String.class);

    }
}