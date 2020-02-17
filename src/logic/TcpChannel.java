package logic;

import javax.swing.*;
import java.io.*;
import java.net.BindException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

/**
 * Created by Kimiya :) on 03/07/2017.
 */
public class TcpChannel{
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    public TcpChannel(SocketAddress socketAddress, int timeout) {
        socket = new Socket();
        try {
            socket.connect(socketAddress, timeout);
        } catch (Exception e) {
            System.err.println(e);
        }
        createIO();
    }

    public TcpChannel(Socket socket, int timeout) {

        this.socket = socket;
        try {
            socket.setSoTimeout(timeout);
        } catch (SocketException e) {
            e.getStackTrace();
        }
        createIO();
    }

    private void createIO() {
        try {
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    /**
     * Try to read specific count from input stream
     */
    public byte[] read(final int count) throws IOException {
        byte[] bytes = new byte[count];
            inputStream.read(bytes, 0, count);
        return bytes;
    }

    /**
     * Write bytes on output stream
     */
    public void write(byte[] data) {
        try {
            outputStream.write(data);
        } catch (IOException e) {
            int temp = JOptionPane.showOptionDialog(null, "connection problem", "error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (temp == JOptionPane.OK_OPTION || temp == JOptionPane.CANCEL_OPTION) {
                System.exit(0);
                e.printStackTrace();
            }
        }


    }

    /**
     * Check socket’s connectivity
     */

    public boolean isConnected() {
        return socket.isConnected();
    }

    /**
     * Try to close socket and input-output streams
     */
    public void closeChannel() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

    public String getIP(){
        return socket.getLocalAddress().toString();
    }


}