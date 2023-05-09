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
    private static ReliableServerSocket reliableServerSock;
    private static ReliableSocket reliableSock;
    private static InetSocketAddress address;
    public static void initConnection(Integer port) {
        Selector selector = null;
        DatagramChannel channel = null;
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            address = new InetSocketAddress(port);
            channel.bind(address);
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            reliableServerSock = new ReliableServerSocket(port+1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        assert selector != null;
        connect(selector, buffer, port);
    }

    public static void connect(Selector selector, ByteBuffer buffer, Integer port) {
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
                    DatagramChannel clientChannel = (DatagramChannel) key.channel();
                    buffer.clear();
                    try {
                        reliableSock = (ReliableSocket) reliableServerSock.accept();
                        System.out.println(reliableSock.getRemoteSocketAddress());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    SocketAddress clientAddress = null;
                    try {
                        clientAddress = clientChannel.receive(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    buffer.flip();
                    String jsonRequest = new String(buffer.array(), 0, buffer.limit());
                    Transmitter transmitter = ServerState.getGson().fromJson(jsonRequest, Transmitter.class);

                    String resp = ServerState.getCollectionReceiver().processTransmitter(transmitter);
                    System.out.println(resp);
                    String jsonResponse = ServerState.getGson().toJson(resp);

                    buffer.clear();
                    buffer.put(jsonResponse.getBytes());
                    buffer.flip();

                    try {
                        clientChannel.send(buffer, clientAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Send acknowledgment to the client

                    iterator.remove();
                }
            }
        }
    }
}

