import logic.MessageManager;

import tools.IButtonListener;
import tools.ISendText;
import view.Frames.GameField;
import view.Frames.RequestsFrame;
import view.Frames.SelectModeFrame;
import view.dialogs.WaitDialog;


/**
 * Created by Kimiya :) on 06/07/2017.
 */
public class Main {
    public static void main(String[] args) {


        SelectModeFrame selectModeFrame = new SelectModeFrame();
        while (!selectModeFrame.getDoneChoosing()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        if (selectModeFrame.getHost()) {
            MessageManager serverMessageManager = new MessageManager(selectModeFrame.getPort());
            serverMessageManager.setUserName(selectModeFrame.getClientName());
            RequestsFrame requestsFrame = new RequestsFrame(serverMessageManager);
            serverMessageManager.setRequestsFrame(requestsFrame);
        } else {
            MessageManager messageManager = new MessageManager(selectModeFrame.getIp(), selectModeFrame.getPort());
            WaitDialog wait = new WaitDialog(messageManager);
           messageManager.setUserName(selectModeFrame.getClientName());
            messageManager.setWaitDialog(wait);
            messageManager.sendRequest(selectModeFrame.getClientName());}



        IButtonListener buttonListener = new IButtonListener() {
            @Override
            public void onAcceptClicked(String ip) {

            }

            @Override
            public void onRejectClicked(String ip) {

            }

            @Override
            public void onReadyClicked() {

            }

            @Override
            public void onCancelClicked() {

            }

            @Override
            public void onHouseClicked(int house) {

            }

            @Override
            public void onLeaveClicked() {

            }

            @Override
            public void onWaitCancelClicked() {

            }
        };

        ISendText iSendText = new ISendText() {
            @Override
            public void onEnterClicked(String text) {

            }
        };





    }
}
