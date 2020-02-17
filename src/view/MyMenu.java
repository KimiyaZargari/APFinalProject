package view;

import view.Frames.GameField;
import view.Frames.HistoryFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static view.Frames.SelectModeFrame.font;

/**
 * Created by Kimiya :) on 08/07/2017.
 */
public class MyMenu extends JMenuBar {
    private static JMenu file, help;
    private static JMenuItem conversationHistory, exit;


    public MyMenu() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e);
        }

        //create menu objects
        file = new JMenu("File");
        help = new JMenu("Help");

        file.setFont(font);
        help.setFont(font);

        //create menu items
        conversationHistory = new JMenuItem("Conversations History");
        exit = new JMenuItem("Exit");

        conversationHistory.setFont(font);
        exit.setFont(font);

        // exitItem.setToolTipText("Exit");


        //handler
        Handler handler = new Handler();


        conversationHistory.addActionListener(handler);
        exit.addActionListener(handler);

        help.addActionListener(handler);
        conversationHistory.addActionListener(handler);
        exit.addActionListener(handler);

        //add items to menu objects
        file.add(conversationHistory);
        file.add(exit);

        //add menu objects to bar
        add(file);
        add(help);

    }

    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == conversationHistory) {
                System.out.println("history");
                HistoryFrame historyFrame = new HistoryFrame();
                historyFrame.setVisible(true);
            }

            if (event.getSource() == exit) {

            }

            if (event.getSource() == help) {
            }
        }
    }
}
