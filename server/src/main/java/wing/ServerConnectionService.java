package wing;

import wing.data.Transmitter;
import wing.managment.CollectionReceiver;
import wing.managment.ServerState;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class ServerConnectionService {
    public static void initConnection(Integer port) {
        Selector selector = null;
        DatagramChannel channel = null;
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(port));
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
                    DatagramChannel clientChannel = (DatagramChannel) key.channel();
                    buffer.clear();

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
                }

                iterator.remove();
            }
        }
    }
}

