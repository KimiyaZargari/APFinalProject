package view.Panels;

import tools.IButtonListener;
import tools.ISendText;
import view.Frames.GameField;
import view.ReadImage;
import view.ShipsPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static view.Frames.SelectModeFrame.scH;
import static view.Frames.SelectModeFrame.scW;
import static view.Frames.SelectModeFrame.font;

/**
 * Created by Kimiya :) on 08/07/2017.
 */
public class ArrangePanel extends JPanel{
    private JButton resetButton, readyButton, leaveButton;
    public static ChatPanel chatPanel;
    private IButtonListener buttonListener;

    private boolean removeLeave = false;
    private String name;

    private JPanel panel1, panel2;
    private JLabel label1;

    public ShipsPane panes;
    private GamePanel gp, gp2;
    private JPanel transparentPanel;

    private boolean whichPanel = true;


    //public ChatPanel get
    public ArrangePanel(ISendText sendText, IButtonListener buttonListener){

        transparentPanel = new JPanel();

        gp  = new GamePanel(buttonListener);
        gp2 = new GamePanel(buttonListener);

        this.buttonListener = buttonListener;

        Handler handler = new Handler();

        setBorder(BorderFactory.createLineBorder(Color.black, (int)(scH/300)));
        setLayout(new BorderLayout());
        panes = new ShipsPane();
        panes.applyPanel(gp);
        setBackground(new Color(235, 245, 251));


        panel1 = new JPanel(new BorderLayout());
        label1 = new JLabel("   Please arrange your field:");
        label1.setFont(new Font("Comic Sans Ms", 1, (int) scH / 60));
        panel1.setBounds(0, 0, (int)(scW/7), (int)(scH/40));
        panel1.setBackground(new Color(235, 245, 251));
        panel1.add(label1, BorderLayout.WEST);
        label1.setOpaque(false);

        panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createLineBorder(Color.black, (int)(scH/300)));
        panel2.setBounds(0, (int)(scH/1.27), (int)(scW/2.42), (int)(scH/10.3));
        panel2.setBackground(Color.WHITE);

        resetButton = new JButton("Reset");
        readyButton = new JButton("Ready");
        readyButton.setFont(font);
        resetButton.setFont(font);

        readyButton.addActionListener(handler);
        resetButton.addActionListener(handler);

        panel2.add(resetButton);
        panel2.add(readyButton);

        panes.applyPanel(panel2);
        panes.applyPanel(panel1);

        chatPanel = new ChatPanel(sendText);
        panes.add(chatPanel);


        panes.makeShipsSticker();
        add(panes);

    }

    public void goToAttackPanel(){
        Handler handler = new Handler();

        gp.setAttackFlag(true);

        if(removeLeave == false){
            leaveButton = new JButton("Leave");
            leaveButton.setFont(font);
            leaveButton.addActionListener(handler);

        }
        removeLeave = true;

        //panes.removeShips();
        panes.removeredundant();
        panes.setShipsInvisible();
        panes.setDragEnable(false);

        panel2.remove(resetButton);
        panel2.remove(readyButton);


        panel2.add(leaveButton);

        if(whichPanel == false){
            panes.remove(gp2);
            panes.add(gp, new Integer(0));
        }

        label1.setText("   Your Turn:");

        repaint();

    }

    public void goToMyPanel(){
        whichPanel = false;
        panes.setDragEnable(false);
        panes.remove(gp);
        panes.add(gp2, new Integer(0));
        panes.setShipsVisible();
        //String s = chatPanel.getOpponentName();
        label1.setText("   " + name + "'s" + " Turn:");
        addWaternExplosion();

    }


    public void addWaternExplosion(){

        for( Integer k : GamePanel.waters){
            if( ! k.equals(new Integer(0))){
                panes.addWaterSticker((int) k);
            }
        }

        for( Integer k : GamePanel.explosions){
            if( ! k.equals(new Integer(0))){
                panes.addExplosionSticker((int) k);
            }
        }
    }
    public void showWater(int n){
        changeIcon("water.png", n);
    }

    public void showExplosion(int n){
        changeIcon("explosion.png", n);
    }

    public void changeIcon(String s, int n){
        JButton[] buttonsArray = gp.getButtonsArray();

        buttonsArray[n].setIcon(ReadImage.makeIcon(s, (int) (scW / 3) / 10, (int) (scW / 3) / 10));
        buttonsArray[n].setBackground(new Color(131, 209, 232));
        buttonsArray[n].setBorder(BorderFactory.createLineBorder(new Color(133, 193, 233)));
        buttonsArray[n].setPreferredSize(new Dimension((this.getWidth() - (int) (scW / 50)), (int) (scH / 50)));
        repaint();
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }

    public GamePanel getGp() {
        return gp;
    }

    public ShipsPane getPanes(){ return panes;}

    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == resetButton) {
                panes.resetShips();
                repaint();

            }
            if (event.getSource() == readyButton) {
                if(readyButton.getText().equals("Ready")){

                    panes.setDragEnable(false);
                    buttonListener.onReadyClicked();
                    readyButton.setText("Cancel");


                    int boundX = (int) (scW / 30);
                    int boundY = (int) (scH / 12);
                    int gameFieldW = (int) (scW / 3);
                    JPanel transparentPanel = new JPanel();
                    //transparentPanel.setBackground(new Color(0,0,0,64));
                    //transparentPanel.setBackground(Color.BLACK);
                    transparentPanel.setOpaque(false);
                    transparentPanel.setBounds(boundX, boundY, gameFieldW, gameFieldW);

                    panes.applyPanel(transparentPanel);
                    panes.repaint();

                    ////
                }
                else{
                    buttonListener.onCancelClicked();
                    readyButton.setText("Ready");
                    panes.remove(transparentPanel);
                    panes.setDragEnable(true);
                    /////// enable panel
                }

            }

            if (event.getSource() == leaveButton) {
                buttonListener.onLeaveClicked();
            }

        }
    }

    public void setName(String mname){
        name = mname;
    }
    public String getName(){
        return name;
    }
}
