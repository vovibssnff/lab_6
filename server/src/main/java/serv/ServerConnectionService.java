package serv;

import net.rudp.ReliableServerSocket;
import net.rudp.ReliableSocket;
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

public class ServerConnectionService {
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
                    try {
                        clientAddress = clientChannel.receive(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    buffer.flip();
                    String jsonRequest = new String(buffer.array(), 0, buffer.limit());
                    Transmitter transmitter = ServerState.getGson().fromJson(jsonRequest, Transmitter.class);

                    String resp = ServerState.getCollectionReceiver().processTransmitter(transmitter);
                    //System.out.println(resp);
                    String jsonResponse = ServerState.getGson().toJson(resp);

                    buffer.clear();
                    buffer.put(jsonResponse.getBytes());
                    buffer.flip();

                    try {
                        clientChannel.send(buffer, clientAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    iterator.remove();
                }
            }
        }
    }
}

