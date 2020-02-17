package view.Panels;

import tools.ISendText;
import view.ReadImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static view.Frames.SelectModeFrame.scH;
import static view.Frames.SelectModeFrame.scW;
import static view.Frames.SelectModeFrame.font;

/**
 * Created by Kimiya :) on 08/07/2017.
 */
public class ChatPanel extends JPanel {
    private ISendText sendText;
    private java.util.List<ChatMePanel> textList;
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
    private static Image background = ReadImage.readImage("bc.png", (int)(scW/(1.5)), (int)(scH));
    private String guestName = "guest";
    private String opponentName;

    public ChatPanel(ISendText sendText) {
        this.sendText = sendText;
        textList = new ArrayList<>();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e);
        }

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, (int) (scH / 400)));
        setBounds(x, y, w, h);
        setLayout(new BorderLayout());

        createPanel();
        setVisible(true);

    }


    @Override
    protected void paintComponent(Graphics g) {
        //Image background = ReadImage.readImage("bc.png", (int)(scW/(1.5)), (int)(scH/(1.5)));
        super.paintComponent(g);
        if (background != null) {
            //xi = (com.company.Menu.getJLabel().getWidth() / 2 - background.getWidth() / 2);
            //yi = (com.company.Menu.getJLabel().getHeight() / 2 - background.getHeight() / 2);

            g.drawImage(background, 0, 0, this);

        }

    }

    public void createPanel() {

        gbc = new GridBagConstraints();

        label1 = new JLabel(" Chat to " + guestName + ":");
        label1.setFont(new Font("Comic Sans Ms", 1, (int) scH / 80));
        label1.setBorder(BorderFactory.createLineBorder(Color.BLACK, (int) (scH / 400)));
        label1.setPreferredSize(new Dimension(100, 100));
        add(label1, BorderLayout.NORTH);

        textField1 = new JTextField("Type here...");
        textField1.setFont(new Font("Comic Sans Ms", 1, (int) scH / 70));
        textField1.setBorder(BorderFactory.createLineBorder(Color.BLACK, (int) (scH / 400)));
        textField1.setPreferredSize(new Dimension(100, 100));
        textField1.setOpaque(false);
        add(textField1, BorderLayout.SOUTH);
        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                JTextComponent component = (JTextComponent) e.getSource();
                component.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                JTextComponent component = (JTextComponent) e.getSource();
                if (component.getText().equals("")) {
                    component.setText("Type here...");
                }
            }
        });

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

        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println(textField1.getText());
                    ChatPanel.ChatMePanel cmp = new ChatMePanel("You", false, "0");
                    typedMessage = textField1.getText();
                    if (typedMessage.equals("")) {
                        //do nothing
                    } else {

                        sendText.onEnterClicked(typedMessage);
                        cmp.messageArea.setText(typedMessage);
                        textField1.setText("");
                        yy++;
                        chats.add(cmp);
                        repaint();
                    }
                }
            }
        });
    }





    public void opponentMessage(String msg, String opponentName) {
        this.opponentName = opponentName;
        xx = 1;
        ChatPanel.ChatMePanel cmp = new ChatMePanel(opponentName, false, "0");
        cmp.messageArea.setText(msg);
        chats.add(cmp);
        textList.add(cmp);
        repaint();
        xx = 0;
    }

    public String getTypedMessage() {
        return typedMessage;
    }


    public class ChatMePanel extends JLayeredPane {

        JLabel nameLabel, timeLabel;
        private JTextArea messageArea;

        ChatMePanel(String name, boolean timeBoolean, String mtime) {

            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("bc.png"));
            } catch (Exception e) {
                System.out.println(e);
            }
            Icon icon = new ImageIcon(img);
            JLabel label = new JLabel(icon);

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

            ZonedDateTime zdt = ZonedDateTime.now();
            timeLabel = new JLabel(zdt.getHour() + ":" + zdt.getMinute());
            timeLabel.setFont(new Font("Comic Sans Ms", 1, (int) scH / 70));
            timeLabel.setLocation(30, 30);
            timeLabel.setOpaque(false);
            timeLabel.setBounds((int) (scW / 13), (int) (scH / 20), (int) (scW / 20), (int) (scH / 20));

            if(timeBoolean == true){
                timeLabel.setText(mtime);
                nameLabel.setText(name);
            }

            add(nameLabel);
            add(messageArea);
            add(timeLabel);
            setVisible(true);

            // setLayout(new BorderLayout());
        }

        public JTextArea getMessageArea() {
            return messageArea;
        }

        public JLabel getNameLabel() {
            return nameLabel;
        }

        public JLabel getTimeLabel() {
            return timeLabel;
        }

    }

    public java.util.List<ChatMePanel> getTextList() {
        return textList;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setYy(int mYy) {
        yy = mYy;
    }

    public int getYy() {
        return yy;
    }
}
