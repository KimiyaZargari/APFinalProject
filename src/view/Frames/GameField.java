package view.Frames;

import tools.IButtonListener;
import tools.ISendText;
import view.MyMenu;
import view.Panels.ArrangePanel;
import view.dialogs.LoseWinDialog;

import javax.swing.*;

import static view.Frames.SelectModeFrame.scH;
import static view.Frames.SelectModeFrame.scW;

/**
 * Created by Kimiya :) on 08/07/2017.
 */
public class GameField extends JFrame {
    private ArrangePanel arrangePanel;
    private JButton[][] buttonArray = new JButton[10][10];
    private boolean opponentReady = false;
    private boolean playerReady = false;


    public GameField(ISendText sendText, IButtonListener buttonListener) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e);
        }
        setSize((int) (scW / 1.5666), (int) scH - (int) (scH / 18));
        setTitle("My Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);


        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setJMenuBar(new MyMenu());

        arrangePanel = new ArrangePanel(sendText, buttonListener);
        add(arrangePanel);

        //attackPanel = new AttackPanel(sendText);

        setVisible(true);

    }

    public void handlePanels() {
       /* remove(arrangePanel);
        attackPanel = new AttackPanel((iSendText));
        //add(attackPanel);

        myFieldPanel = new MyFieldPanel(iSendText);
        add(myFieldPanel);

        setVisible(true);*/
    }

    public void goToAttackPanel(){

        arrangePanel.goToAttackPanel();
    }
    public void showDialog(String s){
        new LoseWinDialog(s);
    }

    public void setOpponentReady(boolean opponentReady) {
        this.opponentReady = opponentReady;
    }

    public void setPlayerReady(boolean playerReady) {
        this.playerReady = playerReady;
    }
    public boolean getPlayerReady(){
        return playerReady;
    }

    public ArrangePanel getArrangePanel() {
        return arrangePanel;
    }

    public boolean getOpponentReady(){return opponentReady;}
}
