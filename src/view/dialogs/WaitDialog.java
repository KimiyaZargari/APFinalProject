package view.dialogs;

import tools.IButtonListener;

import javax.swing.*;
import java.awt.*;

import static view.Frames.SelectModeFrame.scH;
import static view.Frames.SelectModeFrame.scW;

/**
 * this dialog will be shown to the client while waiting for the host to accept.
 * Created by Pegah :) on 07/07/2017.
 */
public class WaitDialog extends JDialog {
    private JLabel label;
    private  JButton cancel;
    private IButtonListener buttonListener;

    public WaitDialog( IButtonListener buttonListener) {
        this.buttonListener = buttonListener;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.getStackTrace();
        }

        setSize((int) (scW / 4), (int) (scH / 5));
        setLayout(null);
        setTitle("Please Wait...");

        label = new JLabel("Waiting for the host to join...");
        label.setFont(new Font("Comic Sans Ms", 1, (int) scH / 60));
        label.setBounds((int) scW / 25, (int) scH / 20, (int) scW / 2, (int) scH / 30);
        cancel = new JButton("Cancel");
        cancel.setBounds((int) scW / 11, (int) scH / 10, (int) scW / 20, (int) scH / 30);
        cancel.addActionListener(e -> {
            buttonListener.onWaitCancelClicked();
        });

        add(label);
        add(cancel);

        setFont(new Font("Comic Sans Ms", 1, (int) scH / 40));

        setVisible(true);

    }

    public void setLabelText(String str) {
        label.setText(str);
        repaint();
    }

     public void setColor(Color color) {
    getContentPane().setBackground(color);

    }

    public void removeCancel(){
        remove(cancel);
    }
}