package logic;


import logic.messages.*;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Kimiya :) on 05/07/2017.
 */
public class NetworkHandler extends Thread {
    private TcpChannel tcpChannel;
    private Queue<byte[]> sendQueue;
    private Queue<byte[]> receivedQueue;
    private INetworkHandlerCallback iNetworkHandlerCallback;
    private ReceivedMessageConsumer consumerThread;

    public NetworkHandler(SocketAddress socketAddress, INetworkHandlerCallback iNetworkHandlerCallback) {
        this(iNetworkHandlerCallback);
        tcpChannel = new TcpChannel(socketAddress, 1000);
        consumerThread = new ReceivedMessageConsumer();
        consumerThread.start();

    }

    public NetworkHandler(Socket socket, INetworkHandlerCallback iNetworkHandlerCallback) {
        this(iNetworkHandlerCallback);

        tcpChannel = new TcpChannel(socket, 1500);
        consumerThread = new ReceivedMessageConsumer();
        consumerThread.start();

    }

    private NetworkHandler(INetworkHandlerCallback iNetworkHandlerCallback) {
        sendQueue = new LinkedList<>();
        receivedQueue = new LinkedList<>();
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;

    }

    public void sendMessage(BaseMessage baseMessage) {
        sendQueue.add(baseMessage.getSerialized());

    }

    /**
     * While channel is connected and stop is not called:
     * if sendQueue is not empty, then poll a message and send it
     * else if readChannel() is returning bytes, then add it to receivedQueue
     */

    @Override
    public void run() {
        while (!this.isInterrupted() && tcpChannel.isConnected()) {
            byte[] received;
            if (!sendQueue.isEmpty()) {
                tcpChannel.write(sendQueue.poll());
            } else if ((received = readChannel()) != null) {
                System.out.println("receiving");
                receivedQueue.add(received);
            } else {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }

        }
    }

    /**
     * Kill the thread and close the channel.
     */

    public void stopSelf() {
        tcpChannel.closeChannel();
        consumerThread.interrupt();
        this.interrupt();

    }

    /**
     * Try to read some bytes from the channel.
     */
    private byte[] readChannel() {
        byte[] message = null;
        try {
            byte[] messageLength = tcpChannel.read(4);
            message = new byte[ByteBuffer.wrap(messageLength).getInt()];
            byte[] tempMessage = tcpChannel.read(message.length - 4);
            for (int i = 0; i < 4; i++)
                message[i] = messageLength[i];
            for (int i = 4; i < message.length; i++) {
                message[i] = tempMessage[i - 4];
            }
        } catch (IOException e) {
            e.getStackTrace();
        } catch (NegativeArraySizeException e) {
            iNetworkHandlerCallback.onSocketClosed();
            consumerThread.interrupt();
        }
        return message;
    }

    private class ReceivedMessageConsumer extends Thread {
        /**
         * While channel is connected and stop is not called:
         * if there exists message in receivedQueue, then create a message object
         * from appropriate class based on message type byte and pass it through onMessageReceived
         * else if receivedQueue is empty, then sleep 100 ms
         */
        @Override
        public void run() {
            while (tcpChannel.isConnected() && !Thread.currentThread().isInterrupted()) {
                if (!receivedQueue.isEmpty()) {
                    byte[] serialized = receivedQueue.poll();
                    byte messageType;
                    try {
                        messageType = serialized[5];
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }
                    BaseMessage message = null;
                    switch (messageType) {
                        case MessageTypes.SEND_REQUEST:
                            message = new SendRequest(serialized);
                            break;
                        case MessageTypes.ACCEPT_REJECT:
                            message = new AcceptMessage(serialized);
                            break;
                        case MessageTypes.OPPONENT_READY:
                            message = new OpponentReadyMessage(serialized);
                            break;
                        case MessageTypes.OPPONENT_LEFT:
                            message = new OpponentLeftMessage(serialized);
                            break;
                        case MessageTypes.TEXT_MESSAGE:
                            message = new TextMessage(serialized);
                            break;
                        case MessageTypes.HOUSE_HIT:
                            message = new HouseHitMessage(serialized);
                            break;
                        case MessageTypes.IS_SHIP:
                            message = new IsShipMessage(serialized);
                            break;
                        case MessageTypes.Server_name:
                            message = new ServerNameMessage(serialized);
                            break;
                        case MessageTypes.CANCEL_REQUEST:
                            message = new CancelRequestMessage(serialized);

                }
                iNetworkHandlerCallback.onMessageReceived(message);
            } else{
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}

    public TcpChannel getTcpChannel() {
        return tcpChannel;
    }

    public String getIP() {
        return tcpChannel.getIP();

    }

public interface INetworkHandlerCallback {
    void onMessageReceived(BaseMessage baseMessage);

    void onSocketClosed();
}
}
