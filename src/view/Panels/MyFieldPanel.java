package view.Panels;

import tools.ISendText;
import view.ReadImage;
import view.ShipsPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static view.Frames.SelectModeFrame.font;
import static view.Frames.SelectModeFrame.scH;
import static view.Frames.SelectModeFrame.scW;

/**
 * Created by Asus on 7/9/2017.
 */
public class MyFieldPanel  extends JPanel {
    private JButton leaveButton;
    private GamePanel gp;

    private ShipsPane panes;
    public MyFieldPanel(ISendText sendText){

        Handler handler = new Handler();

        setBorder(BorderFactory.createLineBorder(Color.black, (int)(scH/300)));
        setLayout(new BorderLayout());
        panes = new ShipsPane();
        panes.applyPanel(gp);
        setBackground(new Color(235, 245, 251));

        JPanel panel1 = new JPanel(new BorderLayout());
        JLabel label1 = new JLabel("   Your Turn:");
        label1.setFont(new Font("Comic Sans Ms", 1, (int) scH / 60));
        panel1.setBounds(0, 0, (int)(scW/7), (int)(scH/40));
        panel1.setBackground(new Color(235, 245, 251));
        panel1.add(label1, BorderLayout.WEST);

        JPanel panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createLineBorder(Color.black, (int)(scH/300)));
        panel2.setBounds(0, (int)(scH/1.27), (int)(scW/2.42), (int)(scH/10.3));
        panel2.setBackground(Color.WHITE);

        leaveButton = new JButton("Leave");
        leaveButton.setFont(font);
        leaveButton.addActionListener(handler);
        panel2.add(leaveButton);

        panes.applyPanel(panel2);
        panes.applyPanel(panel1);
        panes.add(ArrangePanel.chatPanel);


        panes.makeShipsSticker();

        GamePanel.waters.add(2);
        GamePanel.waters.add(20);
        GamePanel.waters.add(44);
        GamePanel.waters.add(100);

        GamePanel.explosions.add(0);
        GamePanel.explosions.add(70);
        GamePanel.explosions.add(59);
        GamePanel.explosions.add(3);

        setEnabled(false);
        panes.setEnabled(false);


        int boundX = (int) (scW / 30);
        int boundY = (int) (scH / 12);
        int gameFieldW = (int) (scW / 3);

        JLabel transparentPanel = new JLabel();
        transparentPanel.setBackground(new Color(0,0,0,64));
        transparentPanel.setOpaque(true);
        transparentPanel.setBounds(boundX, boundY, gameFieldW, gameFieldW);

        panes.add(transparentPanel);

        addWaternExplosion();


        add(panes);

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


    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == leaveButton) {

            }
        }
    }


}
