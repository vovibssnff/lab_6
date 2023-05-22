package cl;

import cmn.OutputEngine;
import cmn.service.Transmitter;
import cl.managment.ProgramState;
import net.rudp.ReliableSocket;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ScheduledExecutorService;

public class ClientConnectionService {
    private static InetSocketAddress serverAddress;
    private static SocketChannel clientChannel;

    public static void initConnection() {
        try {
            clientChannel = SocketChannel.open();
            serverAddress = new InetSocketAddress("localhost", 2222);
            clientChannel.connect(serverAddress);
            clientChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendRequest(Transmitter message) {
        String jsonRequest = ProgramState.getGson().toJson(message);
        ByteBuffer buffer = ByteBuffer.wrap(jsonRequest.getBytes());

        try {
            clientChannel.write(buffer);
            buffer.clear();

            while (clientChannel.read(buffer) == 0) {
                // Waiting for response
            }
            buffer.flip();

            String jsonResponse = new String(buffer.array(), 0, buffer.limit());
            return ProgramState.getGson().fromJson(jsonResponse, String.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}




