package wing;

import wing.managment.ServerState;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

//public class wing.ServerConnectionService {
//
//    private static ByteBuffer buffer = ByteBuffer.allocate(1024);
//
//    public static void connect() {
//        DatagramChannel channel = null;
//        try {
//            channel = DatagramChannel.open();
//            channel.bind(new InetSocketAddress(8080));
//            ByteBuffer buffer = ByteBuffer.allocate(1024);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        while (true) {
//            buffer.clear();
//            try {
//                channel.receive(buffer);
//                buffer.flip();
//                String jsonRequest = new String(buffer.array(), 0, buffer.limit());
//                System.out.println(ServerState.getGson().fromJson(jsonRequest, String.class));
//                String responce = "abobus_sus_amogus";
//                String jsonResponce = ServerState.getGson().toJson(responce);
//                buffer.clear();
//                buffer.put(jsonResponce.getBytes());
//                buffer.flip();
//                channel.send(buffer, new InetSocketAddress("localhost", 8081));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

public class ServerConnectionService {
    private static DatagramChannel channel;
    public static void connect() {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            channel = DatagramChannel.open();
            channel.bind(new InetSocketAddress(8080));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            buffer.clear();
            try {
                channel.receive(buffer);
                buffer.flip();
                //String jsonRequest = new String(buffer.array(), 0, buffer.limit());
                int sequenceNumber = buffer.getInt(); // Assuming the first 4 bytes of the packet is the sequence number
                String jsonRequest = new String(buffer.array(), 1, buffer.limit() - 1); // Assuming the remaining bytes are the request data

                System.out.println(ServerState.getGson().fromJson(jsonRequest, String.class));
                String response = "abobus_sus_amogus";
                String jsonResponse = ServerState.getGson().toJson(response);

                buffer.clear();
                buffer.putInt(sequenceNumber); // Put the sequence number in the buffer
                buffer.put(jsonResponse.getBytes());
                buffer.flip();

                // Send packet and wait for acknowledgement
                boolean isAckReceived = false;
                while (!isAckReceived) {
                    channel.send(buffer, new InetSocketAddress("localhost", 8081));
                    isAckReceived = waitForAck(sequenceNumber);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static boolean waitForAck(int sequenceNumber) throws IOException {
        ByteBuffer ackBuffer = ByteBuffer.allocate(4);
        channel.receive(ackBuffer);
        ackBuffer.flip();
        int ackSequenceNumber = ackBuffer.getInt();
        ackBuffer.clear();

        return ackSequenceNumber == sequenceNumber;
    }
}

