package cl;

import cmn.OutputEngine;
import cmn.service.Transmitter;
import cl.managment.ProgramState;

import java.io.IOException;
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
        byte[] receiveData = new byte[2048 * 2048];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            socket.setSoTimeout(5000);
            socket.receive(receivePacket);

        } catch (SocketTimeoutException e) {
            System.out.println(OutputEngine.serverResponseError());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
        return ProgramState.getGson().fromJson(jsonResponse, String.class);

    }
}