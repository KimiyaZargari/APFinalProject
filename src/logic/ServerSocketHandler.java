package logic;

import javax.sound.sampled.Port;
import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.nio.channels.UnresolvedAddressException;

/**
 * Created by Kimiya :) on 05/07/2017.
 */
public class ServerSocketHandler extends Thread {
    private ServerSocket serverSocket;
    private NetworkHandler.INetworkHandlerCallback iNetworkHandlerCallback;
    private IServerSocketHandlerCallback iServerSocketHandlerCallback;

    public ServerSocketHandler(int port, NetworkHandler.INetworkHandlerCallback iNetworkHandlerCallback, IServerSocketHandlerCallback iServerSocketHandlerCallback) {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(100);
        } catch (BindException e) {
            int temp = JOptionPane.showOptionDialog(null, "port unreachable", "error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (temp == JOptionPane.OK_OPTION || temp == JOptionPane.CANCEL_OPTION) {
                System.exit(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
        this.iServerSocketHandlerCallback = iServerSocketHandlerCallback;
    }

    //TODO check while condition

    @Override
    public void run() {
        while (!serverSocket.isClosed() && !Thread.currentThread().isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                // System.out.println("accepted");
                NetworkHandler networkHandler = new NetworkHandler(socket, iNetworkHandlerCallback);
                iServerSocketHandlerCallback.onNewConnectionReceived(networkHandler);
            } catch (SocketTimeoutException e) {
                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                    continue;
                }
            } catch (IOException e) {
                e.getStackTrace();
            }
        }

    }

    /**
     * Kill the thread and close the server socket.
     */
    public void stopSelf() {
        this.interrupt();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.getStackTrace();
        }


    }

    public interface IServerSocketHandlerCallback {
        void onNewConnectionReceived(NetworkHandler networkHandler);
    }
}