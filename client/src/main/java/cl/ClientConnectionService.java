package cl;

import cmn.OutputEngine;
import cmn.service.Transmitter;
import cl.managment.ProgramState;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//public class ClientConnectionService {
//    private static InetSocketAddress serverAddress;
//    //private static ReliableSocket reliableSock;
//    private static DatagramSocket socket = null;
//
//    public static void initConnection() {
//        try {
//            socket = new DatagramSocket();
//            serverAddress = new InetSocketAddress("localhost", 2222);
//            //reliableSock = new ReliableSocket(socket);
//            //reliableSock.connect(serverAddress);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String sendRequest(Transmitter message) {
//
//        String jsonRequest = ProgramState.getGson().toJson(message);
//
//        byte[] sendData = jsonRequest.getBytes();
//        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress);
//        try {
//            socket.send(sendPacket);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        byte[] receiveData = new byte[2048 * 2048];
//        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//        try {
//            socket.setSoTimeout(5000);
//            socket.receive(receivePacket);
//
//        } catch (SocketTimeoutException e) {
//            System.out.println(OutputEngine.serverResponseError());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String jsonResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
//        return ProgramState.getGson().fromJson(jsonResponse, String.class);
//
//    }
//}


public class ClientConnectionService {
    private static InetSocketAddress serverAddress;
    private static DatagramSocket socket = null;
    private static final int MAX_CHUNK_SIZE = 1024; // Adjust this value to match the server

    public static void initConnection() {
        try {
            socket = new DatagramSocket();
            serverAddress = new InetSocketAddress("localhost", 2222);
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

        List<byte[]> chunks = receiveResponseChunks();
        //String resp = ProgramState.getGson().fromJson(jsonResponse, String.class);
        return reconstructMessage(chunks);
    }

    private static List<byte[]> receiveResponseChunks() {
        List<byte[]> chunks = new ArrayList<>();
        byte[] receiveData = new byte[MAX_CHUNK_SIZE];

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                socket.setSoTimeout(5000);
                socket.receive(receivePacket);
                byte[] receivedChunk = new byte[receivePacket.getLength()];
                System.arraycopy(receiveData, 0, receivedChunk, 0, receivePacket.getLength());
                chunks.add(receivedChunk);
                if (receivePacket.getLength() < MAX_CHUNK_SIZE) {
                    break; // Last chunk received
                }
            } catch (IOException g) {
                break;
            }
        }
        return chunks;
    }

    private static String reconstructMessage(List<byte[]> chunks) {
        int totalLength = chunks.stream().mapToInt(chunk -> chunk.length).sum();
        byte[] messageBytes = new byte[totalLength];
        ByteBuffer buffer = ByteBuffer.wrap(messageBytes);

        for (byte[] chunk : chunks) {
            buffer.put(chunk);
        }

        return new String(messageBytes);
    }
}