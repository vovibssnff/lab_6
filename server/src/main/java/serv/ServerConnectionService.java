package serv;

import net.rudp.ReliableServerSocket;
import net.rudp.ReliableSocket;
import serv.load.Serializer;
import serv.managment.Collections;
import serv.managment.ServerConnector;
import serv.managment.ServerState;
import cmn.data.Transmitter;

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
    //private static ReliableServerSocket reliableServerSock;
    //private static ReliableSocket reliableSock;
    private static InetSocketAddress address;
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
        ByteBuffer buffer = ByteBuffer.allocate(1024);
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
                    System.out.println(clientChannel.socket());
                    logger.info("Add client " + clientChannel.socket());
                    try {
                        clientAddress = clientChannel.receive(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    buffer.flip();
                    String jsonRequest = new String(buffer.array(), 0, buffer.limit());
                    Serializer.save(Collections.getCollection());
                    logger.info("Request: " + jsonRequest);

                    Transmitter transmitter = ServerState.getGson().fromJson(jsonRequest, Transmitter.class);

                    String resp = ServerState.getCollectionReceiver().processTransmitter(transmitter);
                    //System.out.println(resp);
                    String jsonResponse = ServerState.getGson().toJson(resp);
                    logger.info("Response: " + jsonResponse);

                    buffer.clear();
                    System.out.println(jsonResponse.getBytes().length);
                    buffer.put(jsonResponse.getBytes());
                    buffer.flip();

                    try {
                        clientChannel.send(buffer, clientAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    buffer.clear();
                    iterator.remove();
                }
            }
        }
    }
}

