package view.Panels;

import tools.IButtonListener;
import view.ReadImage;
import view.ShipsPane;
import view.dialogs.LoseWinDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static view.Frames.SelectModeFrame.scH;
import static view.Frames.SelectModeFrame.scW;
import static view.Frames.SelectModeFrame.font;

/**
 * Created by Kimiya :) on 08/07/2017.
 */
public class GamePanel extends JPanel {

    public final static int boundX = (int) (scW / 30);
    public final static int boundY = (int) (scH / 12);
    public final static int gameFieldW = (int) (scW / 3);
    private IButtonListener buttonListener;


    public static ArrayList<Integer> waters = new ArrayList();
    public static ArrayList<Integer> explosions = new ArrayList();

    public int selectedButton;
    private int deadShips = 0;

    public boolean attackFlag = false;

    public void setAttackFlag(boolean attack){
        attackFlag = attack;
    }

    public boolean getAttackFlag(){return attackFlag;}


    JButton[] buttonsArray = new JButton[101];

    public JButton[] getButtonsArray() {
        return buttonsArray;
    }

    public GamePanel( IButtonListener buttonListener) {
        this.buttonListener = buttonListener;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e);
        }
        setBorder(BorderFactory.createLineBorder(new Color(133, 193, 233), (int) (scH / 400)));
        setPreferredSize(new Dimension((int) (scW / 10), (int) (scW / 10)));
        setBounds(boundX, boundY, gameFieldW, gameFieldW);
        createPanel();
    }

    public void createPanel() {
        for (int i = 0; i < 100; i++) {
            JButton button = new JButton();
            button.setIcon(ReadImage.makeIcon("white.png", (int) (scW / 3) / 10, (int) (scW / 3) / 10));
            button.setBackground(new Color(131, 209, 232));
            button.setBorder(BorderFactory.createLineBorder(new Color(133, 193, 233)));
            button.setPreferredSize(new Dimension((this.getWidth() - (int) (scW / 50)), (int) (scH / 50)));
            buttonsArray[i + 1] = button;
            add(button);
        }


        for (int i = 1; i < 101; i++) {
            buttonsArray[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (attackFlag == true) {
                        for (int j = 1; j < 101; j++) {
                            if (e.getSource() == buttonsArray[j]) {
                                selectedButton = j;
                                buttonListener.onHouseClicked(selectedButton);
                            }
                        }
                    }
                }
            });
        }

        setLayout(new GridLayout(10, 10));
    }

    public boolean findShips(int n) {
        for (Integer k : ShipsPane.shippedButtons) {
            if (k.equals(new Integer(n))) {
                // button n is SHIP
                explosions.add(k);
                deadShips++;
                /*if(deadShips == 1){
                    LoseWinDialog loseDialog = new LoseWinDialog("You Lose!");

                }*/
                return true;
            }
            else {
                waters.add(new Integer(n));

            }
        }
        return false;
    }

    public int getDeadShips(){return deadShips;}

}
