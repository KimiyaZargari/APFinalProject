package view.Panels;

import tools.ISendText;
import view.ReadImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.ZonedDateTime;

import static view.Frames.SelectModeFrame.font;
import static view.Frames.SelectModeFrame.scH;
import static view.Frames.SelectModeFrame.scW;

/**
 * Created by Asus on 7/10/2017.
 */
public class ConversationsPanel extends JPanel {
    private ISendText sendText;
    private java.util.List<ChatPanel.ChatMePanel> textList;
    private int x = (int) (scW) - (int) (scW / 1.7);
    private int y = 0;
    private int w = (int) (scW / 4.5);
    private int h = (int) scH - (int) (scH / 8.6);
    private String typedMessage;
    private static int yy = 0, xx = 0;
    private JLayeredPane chats;
    private JLabel label1;
    private JTextField textField1;
    private GridBagConstraints gbc;
    private static Image background = ReadImage.readImage("bc.png", 1000, 5000);
    private String guestName = "guest";
    private String opponentName;


    public void ConversationsPanel() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e);
        }

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, (int) (scH / 400)));
        setBounds(x, y, w, (int) (h / 1.5));
        setLayout(new BorderLayout());

        setVisible(true);

        chats = new JLayeredPane();
        chats.setOpaque(false);

        JScrollPane scroll = new JScrollPane();
        chats.setPreferredSize(new Dimension(w - 100, 90000));
        scroll.setPreferredSize(new Dimension(w - 100, h - 250));
        scroll.setViewportView(chats);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setOpaque(false);
        scroll.setViewportView(chats);


        JPanel tmp = new JPanel();
        tmp.setOpaque(false);
        tmp.add(scroll);
        add(tmp, BorderLayout.CENTER);

        chats.setLayout(null);
    }


    public void chatConversations(String yourName, String mtext, String mtime) {

        if (yourName.equals("You")) {
            ChatMePanel cmp = new ChatMePanel(yourName, true, mtime);
            cmp.messageArea.setText(mtext);
            yy++;
            chats.add(cmp);
            repaint();
        } else {
            xx = 1;
            yy++;
            ChatMePanel cmp = new ChatMePanel(yourName, true, mtime);
            cmp.messageArea.setText(mtext);
            chats.add(cmp);
            repaint();
            xx = 0;
        }
    }


    public class ChatMePanel extends JLayeredPane {

        JLabel nameLabel, timeLabel;
        private JTextArea messageArea;

        ChatMePanel(String name, boolean timeBoolean, String mtime) {

            //add(label, new Integer(0));
            setBorder(BorderFactory.createLineBorder(Color.BLACK, (int) (scH / 400)));
            setBounds(2 + xx * ((int) (scW / 12)), (int) (scH / 70) + (yy) * (int) (scH / 9), (int) (scW / 10), (int) (scH / 10));
            setPreferredSize(new Dimension((int) (scW / 10), (int) (scH / 10)));

            nameLabel = new JLabel();
            nameLabel.setFont(new Font("Comic Sans Ms", 1, (int) scH / 70));
            nameLabel.setLocation(2, 2);
            nameLabel.setOpaque(false);
            nameLabel.setBounds((int) (scW / 200) + xx * (int) (scW / 14), 0, (int) (scW), (int) (scH / 20));

            if (xx == 1) {
                nameLabel.setText(":" + name);
            } else {
                nameLabel.setText(name + ":");
            }

            messageArea = new JTextArea();
            messageArea.setEditable(false);
            messageArea.setOpaque(false);
            messageArea.setFont(font);
            messageArea.setBounds((int) (scW / 100), (int) (scH / 25), (int) (scW), (int) (scH / 20));

            timeLabel = new JLabel();
            timeLabel.setFont(new Font("Comic Sans Ms", 1, (int) scH / 70));
            timeLabel.setLocation(30, 30);
            timeLabel.setOpaque(false);
            timeLabel.setBounds((int) (scW / 13), (int) (scH / 20), (int) (scW / 20), (int) (scH / 20));

            if (timeBoolean == true) {
                timeLabel.setText(mtime);
                nameLabel.setText(name);
            }

            add(nameLabel);
            add(messageArea);
            add(timeLabel);
            setVisible(true);

            // setLayout(new BorderLayout());
        }
    }
}