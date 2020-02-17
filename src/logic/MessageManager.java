package logic;

import logic.messages.*;
import model.JSONExample;
import model.OutputFileWriter;
import tools.IButtonListener;
import tools.ISendText;
import view.Frames.GameField;
import view.Frames.RequestsFrame;


import view.Panels.ChatPanel;
import view.dialogs.WaitDialog;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kimiya :) on 05/07/2017.
 */
public class MessageManager implements ServerSocketHandler.IServerSocketHandlerCallback, NetworkHandler.INetworkHandlerCallback, IButtonListener, ISendText {
    private ServerSocketHandler serverSocketHandler;
    private NetworkHandler networkHandler;
    private List<NetworkHandler> networkHandlerList;
    private List<String> clientNames;
    private String opponentName;
    private RequestsFrame requestsFrame;
    private WaitDialog waitDialog;
    private int rejectedNetworkIndex;
    private GameField gameField;
    private String userName;
    private int houseSelected;
    private int hitShips = 0;


    /**
     * Instantiate server socket handler and start it. (Call this constructor in host mode)
     */
    public MessageManager(int port) {
        this.serverSocketHandler = new ServerSocketHandler(port, this, this);
        serverSocketHandler.start();
        networkHandlerList = new ArrayList<>();
        clientNames = new ArrayList<>();
    }

    /**
     * Instantiate a network handler and start it. (Call this constructor in guest mode)
     */
    public MessageManager(String ip, int port) {
        try {

            System.out.println(ip);
            networkHandler = new NetworkHandler(new Socket(ip, port), this);
            networkHandler.start();
        } catch (UnknownHostException | SocketException e) {
            int temp = JOptionPane.showOptionDialog(null, "connection problem", "error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (temp == JOptionPane.OK_OPTION || temp == JOptionPane.CANCEL_OPTION) {
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create a RequestLoginMessage object and send it through the appropriate network handler.
     */

    public void sendRequest(String name) {
        networkHandler.sendMessage(new SendRequest(name));

    }

    public void sendAcceptMessage(boolean accepted, int i) {
        networkHandlerList.get(i).sendMessage(new AcceptMessage(accepted));


    }

    public void sendServerName(String name) {
        networkHandler.sendMessage(new ServerNameMessage(name));
    }

    public void sendReadyMessage(boolean isReady, NetworkHandler networkHandler) {
        networkHandler.sendMessage(new OpponentReadyMessage(isReady));
        gameField.setPlayerReady(isReady);
        if (isReady && gameField.getOpponentReady()) {
            gameField.goToAttackPanel();
        }

    }

    public void sendLeftMessage(boolean left) {
        networkHandler.sendMessage(new OpponentLeftMessage(left));
        List<ChatPanel.ChatMePanel> messagePanelList = gameField.getArrangePanel().getChatPanel().getTextList();
        JSONExample jsonExample = new JSONExample();
        for (ChatPanel.ChatMePanel aMessagePanel : messagePanelList) {
            jsonExample.createNewObject(aMessagePanel);
        }
        jsonExample.createJSON();
        OutputFileWriter outputFileWriter = new OutputFileWriter("history//" + gameField.getArrangePanel().getName() + ".txt");
        outputFileWriter.write(jsonExample.toString());
        int temp = JOptionPane.showOptionDialog(null, "you left", "error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if (temp == JOptionPane.OK_OPTION || temp == JOptionPane.CANCEL_OPTION) {
            System.exit(0);
        }
    }

    public void sendTextMessage(String text) {
        System.out.println(text);

        networkHandler.sendMessage(new TextMessage(text));
    }

    public void sendHouseHitMessage(int house) {
        this.houseSelected = house;
        networkHandler.sendMessage(new HouseHitMessage(house));
    }

    public void sendIsShipMessage(boolean isShip) {
        networkHandler.sendMessage(new IsShipMessage(isShip));
        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameField.getArrangePanel().goToAttackPanel();
    }

    private void sendCancelRequestMessage(String ip) {
        networkHandler.sendMessage(new CancelRequestMessage(ip));
        waitDialog.setVisible(false);

    }

    private void consumeSendRequest(SendRequest message) {
        clientNames.add(message.getName());
        requestsFrame.addUsersPanel(message.getName(), networkHandlerList.get(networkHandlerList.size() - 1).getIP());


    }

    public void consumeServerName(ServerNameMessage message) {
        opponentName = message.getName();

    }

    private void consumeAcceptMessage(AcceptMessage message) {

        if (message.getBooleanMessage() == 1) {
            waitDialog.setLabelText("accepted. wait a few moments please!");
            waitDialog.removeCancel();
            waitDialog.setColor(new Color(10, 240, 90));
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                e.getStackTrace();
            }
            waitDialog.setVisible(false);
            gameField = new GameField(this, this);


        } else {
            waitDialog.setLabelText("host did not accept");
            waitDialog.removeCancel();
            waitDialog.setColor(Color.RED);
            System.out.println("stopping");
            networkHandler.stopSelf();
        }

    }

    private void consumeReadyMessage(OpponentReadyMessage message) {
        ////////////////////////changed
        gameField.getArrangePanel().setName(opponentName);
        ////////////////////////////////
        if (message.getBooleanMessage() == 1) {
            gameField.setOpponentReady(true);
            if (gameField.getPlayerReady()) {
                gameField.getArrangePanel().goToMyPanel();
            }
        } else {
            gameField.setOpponentReady(false);
        }

    }

    private void consumeLeftMessage(OpponentLeftMessage message) {
        List<ChatPanel.ChatMePanel> messagePanelList = gameField.getArrangePanel().getChatPanel().getTextList();
        JSONExample jsonExample = new JSONExample();
        for (ChatPanel.ChatMePanel aMessagePanel : messagePanelList) {
            jsonExample.createNewObject(aMessagePanel);
        }
        jsonExample.createJSON();
        OutputFileWriter outputFileWriter = new OutputFileWriter("history//" + gameField.getArrangePanel().getName() + ".txt");
        outputFileWriter.write(jsonExample.toString());
        networkHandler.stopSelf();
        int temp = JOptionPane.showOptionDialog(null, "opponent left", "error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if (temp == JOptionPane.OK_OPTION || temp == JOptionPane.CANCEL_OPTION) {

        }
    }

    private void consumeTextMessage(TextMessage message) {
        gameField.getArrangePanel().getChatPanel().opponentMessage(message.getStr(), opponentName);
        gameField.getArrangePanel().getChatPanel().setYy(gameField.getArrangePanel().getChatPanel().getYy() + 1);
    }

    private void consumeHouseHitMessage(HouseHitMessage message) {
        sendIsShipMessage(gameField.getArrangePanel().getGp().findShips(message.getHouse()));

        int deadShips = gameField.getArrangePanel().getGp().getDeadShips();
        if (deadShips == 20) {
            //lose.setVisible(true);
            gameField.showDialog("You Lose!");
            //json
            System.exit(0);
            gameField.getArrangePanel().getPanes().setDragEnable(false);
            gameField.getArrangePanel().goToMyPanel();
            gameField.getArrangePanel().validate();
            gameField.getArrangePanel().repaint();
        } else {
            gameField.getArrangePanel().addWaternExplosion();
            gameField.getArrangePanel().goToMyPanel();
            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameField.getArrangePanel().goToAttackPanel();
            gameField.getArrangePanel().validate();
            gameField.getArrangePanel().repaint();


        }


    }

    private void consumeIsShipMessage(IsShipMessage message) {
        if (message.getBooleanMessage() == 1) {
            gameField.getArrangePanel().showExplosion(houseSelected);
        } else {
            gameField.getArrangePanel().showWater(houseSelected);
        }

        gameField.validate();
        gameField.repaint();

        hitShips++;
        if (hitShips == 20) {
            System.out.println("you won");
            //LoseWinDialog win = new LoseWinDialog("You Win!");
            //win.setVisible(true);
            gameField.showDialog("You Win!");
            gameField.getArrangePanel().getPanes().setDragEnable(false);
            gameField.getArrangePanel().goToMyPanel();
            gameField.getArrangePanel().validate();
            gameField.getArrangePanel().repaint();
        } else {
            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameField.getArrangePanel().goToMyPanel();
            gameField.validate();
            gameField.repaint();
        }


    }

    private void consumeCancelRequestMessage(CancelRequestMessage message) {
        for (int i = 0; i < networkHandlerList.size(); i++) {
            if (message.getStr().equals(networkHandlerList.get(i).getIP())) {
                sendAcceptMessage(false, i);
                rejectedNetworkIndex = i;
                requestsFrame.removeUsersPanel(networkHandlerList.get(rejectedNetworkIndex).getIP());
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
                break;

            }

        }


    }


    /**
     * Add network handler to the list.
     */
    @Override
    public void onNewConnectionReceived(NetworkHandler networkHandler) {
        networkHandlerList.add(networkHandler);
        networkHandler.start();

    }

    /**
     * According to the message type of baseMessage, call corresponding method to use it.
     */
    @Override
    public void onMessageReceived(BaseMessage baseMessage) {
        switch (baseMessage.getMessageType()) {
            case MessageTypes.SEND_REQUEST:
                consumeSendRequest((SendRequest) baseMessage);
                break;
            case MessageTypes.ACCEPT_REJECT:
                consumeAcceptMessage((AcceptMessage) baseMessage);
                break;
            case MessageTypes.OPPONENT_READY:
                consumeReadyMessage((OpponentReadyMessage) baseMessage);
                break;
            case MessageTypes.OPPONENT_LEFT:
                consumeLeftMessage((OpponentLeftMessage) baseMessage);
                break;
            case MessageTypes.TEXT_MESSAGE:
                consumeTextMessage((TextMessage) baseMessage);
                break;
            case MessageTypes.HOUSE_HIT:
                consumeHouseHitMessage((HouseHitMessage) baseMessage);
                break;
            case MessageTypes.IS_SHIP:
                consumeIsShipMessage((IsShipMessage) baseMessage);
                break;
            case MessageTypes.Server_name:
                consumeServerName((ServerNameMessage) baseMessage);
                break;
            case MessageTypes.CANCEL_REQUEST:
                consumeCancelRequestMessage((CancelRequestMessage) baseMessage);

        }
    }

    public void setRequestsFrame(RequestsFrame requestsFrame) {
        this.requestsFrame = requestsFrame;
    }


    public boolean isConnected() {
        return networkHandler.getTcpChannel().isConnected();
    }

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    @Override
    public void onAcceptClicked(String ip) {
        NetworkHandler networkHandler;
        for (int i = 0; i < networkHandlerList.size(); i++) {
            networkHandler = networkHandlerList.get(i);
            if (!ip.equals(networkHandler.getIP())) {
                sendAcceptMessage(false, i);

            } else {
                this.networkHandler = networkHandler;
                opponentName = clientNames.get(i);
                clientNames.clear();
                sendAcceptMessage(true, i);
                sendServerName(userName);
                requestsFrame.setVisible(false);
                try {
                    Thread.currentThread().sleep(10);
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }

        }
        gameField = new GameField(this, this);

    }

    @Override
    public void onRejectClicked(String ip) {
        for (int i = 0; i < networkHandlerList.size(); i++) {
            if (ip.equals(networkHandlerList.get(i).getIP())) {
                sendAcceptMessage(false, i);
                rejectedNetworkIndex = i;
                requestsFrame.removeUsersPanel(networkHandlerList.get(rejectedNetworkIndex).getIP());
                try {
                    Thread.currentThread().sleep(10);
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
                break;

            }

        }
    }

    @Override
    public void onReadyClicked() {
        sendReadyMessage(true, networkHandler);
    }

    @Override
    public void onCancelClicked() {
        sendReadyMessage(false, networkHandler);
    }

    @Override
    public void onEnterClicked(String text) {
        sendTextMessage(text);

    }

    @Override
    public void onHouseClicked(int house) {
        System.out.println(house);
        sendHouseHitMessage(house);

    }

    @Override
    //////////////
    public void onLeaveClicked() {
        sendLeftMessage(true);

    }
    ///////////////

    public void setWaitDialog(WaitDialog waitDialog) {
        this.waitDialog = waitDialog;
    }

    @Override
    public void onWaitCancelClicked() {
        sendCancelRequestMessage(networkHandler.getIP());
    }


    @Override
    public void onSocketClosed() {
        if (networkHandlerList.size() == 0)
            networkHandler.stopSelf();
        else
            networkHandlerList.get(rejectedNetworkIndex).stopSelf();
        networkHandlerList.remove(rejectedNetworkIndex);

    }


    public void setUserName(String userName) {
        this.userName = userName;
    }
}
