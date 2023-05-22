package serv;

import cmn.OutputEngine;
import net.rudp.ReliableServerSocket;
import net.rudp.ReliableSocket;
import serv.load.Serializer;
import serv.managment.CollectionService;
import serv.managment.ServerConnector;
import serv.managment.ServerState;
import cmn.service.Transmitter;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;


public class ServerConnectionService {
    public static Logger logger;
    private static InetSocketAddress address;

    public static void initConnection(Integer port) {
        try {
            address = new InetSocketAddress(port);
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(address);
            serverChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            ByteBuffer buffer = ByteBuffer.allocate(2048);

            logger = ServerConnector.logger;
            while (true) {
                selector.select();

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = serverSocketChannel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ);

                        logger.info("New client connected: " + clientChannel.socket().getRemoteSocketAddress());
                    } else if (key.isReadable()) {
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        buffer.clear();

                        int bytesRead;
                        try {
                            bytesRead = clientChannel.read(buffer);
                        } catch (IOException e) {
                            key.cancel();
                            clientChannel.close();
                            continue;
                        }

                        if (bytesRead == -1) {
                            key.cancel();
                            clientChannel.close();
                            continue;
                        }

                        buffer.flip();
                        String jsonRequest = new String(buffer.array(), 0, buffer.limit());

                        logger.info("Request: " + jsonRequest);

                        Transmitter transmitter = ServerState.getGson().fromJson(jsonRequest, Transmitter.class);

                        String resp = ServerState.getCollectionReceiver().processTransmitter(transmitter);
                        String jsonResponse = ServerState.getGson().toJson(resp);
                        logger.info("Response: " + jsonResponse);

                        ByteBuffer responseBuffer = ByteBuffer.wrap(jsonResponse.getBytes());
                        clientChannel.write(responseBuffer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






