package serv;

import cmn.OutputEngine;
import serv.load.Serializer;
import serv.managment.CollectionService;
import serv.managment.ServerConnector;
import serv.managment.ServerState;
import cmn.service.Transmitter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.logging.Logger;
//
//public class ServerConnectionService {
//    public static Logger logger;
//    //private static ReliableServerSocket reliableServerSock;
//    //private static ReliableSocket reliableSock;
//    private static InetSocketAddress address;
//    public static void initConnection(Integer port) {
//        Selector selector = null;
//        DatagramChannel channel = null;
//        try {
//            address = new InetSocketAddress(port);
//            channel = DatagramChannel.open();
//            channel.configureBlocking(false);
//            channel.bind(address);
//            selector = Selector.open();
//            channel.register(selector, SelectionKey.OP_READ);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ByteBuffer buffer = ByteBuffer.allocate(2048 * 2048);
//        assert selector != null;
//        connect(selector, buffer);
//    }
//
//    public static void connect(Selector selector, ByteBuffer buffer) {
//        logger = ServerConnector.logger;
//        while (true) {
//            try {
//                selector.select();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//            while (iterator.hasNext()) {
//                SelectionKey key = iterator.next();
//
//                if (key.isReadable()) {
//                    SocketAddress clientAddress = null;
//                    DatagramChannel clientChannel = (DatagramChannel) key.channel();
//                    buffer.clear();
//                    //System.out.println(OutputEngine.serverNewConnection() + " " + clientChannel.socket());
//                    logger.info("Add client " + clientChannel.socket());
//                    try {
//                        clientAddress = clientChannel.receive(buffer);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    buffer.flip();
//                    String jsonRequest = new String(buffer.array(), 0, buffer.limit());
//
//                    logger.info("Request: " + jsonRequest);
//
//                    Transmitter transmitter = ServerState.getGson().fromJson(jsonRequest, Transmitter.class);
//
//                    String resp = ServerState.getCollectionReceiver().processTransmitter(transmitter);
//                    //System.out.println(resp);
//                    String jsonResponse = ServerState.getGson().toJson(resp);
//                    logger.info("Response: " + jsonResponse);
//
//                    buffer.clear();
//                    //System.out.println(jsonResponse.getBytes().length);
//                    buffer.put(jsonResponse.getBytes());
//                    buffer.flip();
//
//                    try {
//                        clientChannel.send(buffer, clientAddress);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    buffer.clear();
//                    iterator.remove();
//                }
//            }
//        }
//    }
//}
//




import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.logging.Logger;

public class ServerConnectionService {
    public static Logger logger;
    private static InetSocketAddress address;
    private static final int MAX_CHUNK_SIZE = 1024; // Adjust this value as needed

    public static void initConnection(Integer port) {
        Selector selector = null;
        DatagramChannel channel = null;
        try {
            address = new InetSocketAddress(port);
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.bind(address);
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer buffer = ByteBuffer.allocate(2048 * 2048);
        assert selector != null;
        connect(selector, buffer);
    }

    public static void connect(Selector selector, ByteBuffer buffer) {
        logger = ServerConnector.logger;
        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isReadable()) {
                    SocketAddress clientAddress = null;
                    DatagramChannel clientChannel = (DatagramChannel) key.channel();
                    buffer.clear();
                    logger.info("Add client " + clientChannel.socket());
                    try {
                        clientAddress = clientChannel.receive(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    buffer.flip();
                    String jsonRequest = new String(buffer.array(), 0, buffer.limit());

                    logger.info("Request: " + jsonRequest);

                    Transmitter transmitter = ServerState.getGson().fromJson(jsonRequest, Transmitter.class);

//                    String resp = ServerState.getGson().toJson(ServerState.getCollectionReceiver().processTransmitter(transmitter));
                    String resp = ServerState.getCollectionReceiver().processTransmitter(transmitter);
                    logger.info("Response: " + resp);

                    sendResponse(clientChannel, clientAddress, resp);
                    buffer.clear();
                    iterator.remove();
                }
            }
        }
    }

    private static void sendResponse(DatagramChannel channel, SocketAddress clientAddress, String response) {
        byte[] responseBytes = null;
        if (response!=null) {
            responseBytes = response.getBytes();

        } else {
            responseBytes = "".getBytes();
        }
        int totalLength = responseBytes.length;
        int offset = 0;

        while (offset < totalLength) {
            int chunkSize = Math.min(MAX_CHUNK_SIZE, totalLength - offset);
            ByteBuffer buffer = ByteBuffer.wrap(responseBytes, offset, chunkSize);

            try {
                channel.send(buffer, clientAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }

            offset += chunkSize;
        }
    }
}
