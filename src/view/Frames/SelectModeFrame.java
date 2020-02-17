package view.Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Kimiya :) on 06/07/2017.
 */
public class SelectModeFrame extends JFrame {

    public final static double scW = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public final static double scH = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public final static Font font = new Font("Comic Sans Ms", 0, (int) scH / 70);

    private JPanel panel;
    private JButton exitButton, startButton;
    private JTextField textField1, textField2, textField3, textField4;
    private JRadioButton hostRadioButton1, guestRadioButton2;

    private String clientName, ip;
    private int port;
    private boolean host = true, guest = false, doneChoosing = false;


    public SelectModeFrame() {

        setSize((int) (scW / 3), (int) (scH / 2));
        setLayout(new BorderLayout());
        setTitle("Select Connection Mode");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createPanel();
        add(panel, BorderLayout.CENTER);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
           e.getStackTrace();
        }

        setVisible(true);
    }

    private void createPanel() {
        panel = new JPanel();

        panel.setBorder(BorderFactory.createLineBorder(Color.black, (int) (scH / 200)));

        Handler handler = new Handler();

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(grid);

        JLabel name = new JLabel("Name: ");
        name.setFont(font);

        JLabel hostPort = new JLabel("Port: ");
        hostPort.setFont(font);

        JLabel ip = new JLabel("IP: ");
        ip.setFont(font);

        JLabel guestPort = new JLabel("Port: ");
        guestPort.setFont(font);

        textField1 = new JTextField("");
        textField1.setPreferredSize(new Dimension((int) scW / 8, (int) scH / 50));
        textField1.setFont(font);

        textField2 = new JTextField("");
        textField2.setPreferredSize(new Dimension((int) (scW / 15), (int) scH / 50));
        textField2.setFont(font);

        textField3 = new JTextField("");
        textField3.setPreferredSize(new Dimension((int) (scW / 15), (int) scH / 50));
        textField3.setFont(font);
        textField3.setEditable(false);

        textField4 = new JTextField("");
        textField4.setPreferredSize(new Dimension((int) (scW / 15), (int) scH / 50));
        textField4.setFont(font);
        textField4.setEditable(false);

        hostRadioButton1 = new JRadioButton("Host", true);
        hostRadioButton1.setFont(font);
        hostRadioButton1.addActionListener(handler);

        guestRadioButton2 = new JRadioButton("Guest", false);
        guestRadioButton2.setFont(font);
        guestRadioButton2.addActionListener(handler);

        exitButton = new JButton("Exit");
        startButton = new JButton("Start");
        exitButton.setPreferredSize(new Dimension((int) (scW / 20), (int) (scH / 45)));
        startButton.setPreferredSize(new Dimension((int) (scW / 20), (int) (scH / 45)));
        exitButton.setFont(font);
        startButton.setFont(font);
        exitButton.addActionListener(handler);
        startButton.addActionListener(handler);

        JPanel panel1 = new JPanel();
        panel1.add(name);
        panel1.add(textField1);

        JPanel panel2 = new JPanel();
        panel2.add(hostPort);
        panel2.add(textField2);

        JPanel panel3 = new JPanel();
        panel3.add(ip);
        panel3.add(textField3);
        panel3.add(guestPort);
        panel3.add(textField4);

        JPanel panel4 = new JPanel();
        panel4.add(exitButton);
        panel4.add(startButton);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        panel.add(panel1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        panel.add(hostRadioButton1, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        panel.add(panel2, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;

        panel.add(guestRadioButton2, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(panel3, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(panel4, gbc);


    }

    public String getClientName() {
        return clientName;
    }

    public int getPort() {
        return port;
    }

    public boolean getDoneChoosing() {
        return doneChoosing;
    }

    public boolean getHost() {
        return host;
    }

    public String getIp() {
        return ip;
    }

    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == hostRadioButton1) {
                guestRadioButton2.setSelected(false);
                textField2.setEditable(true);
                textField3.setEditable(false);
                textField4.setEditable(false);
            }
            if (event.getSource() == guestRadioButton2) {
                hostRadioButton1.setSelected(false);
                textField3.setEditable(true);
                textField4.setEditable(true);
                textField2.setEditable(false);
            }
            if (event.getSource() == exitButton) {
                System.exit(0);

            }
            if (event.getSource() == startButton) {
                boolean isComplete = true;

                if (hostRadioButton1.isSelected()) {
                    if ((textField1.getText().equals("")) || (textField2.getText().equals(""))) {
                        JOptionPane.showMessageDialog(null, "Please fill the blankets");
                        isComplete = false;
                    } else {
                        clientName = textField1.getText();
                        host = true;
                        guest = false;

                        try {
                            port = Integer.parseInt(textField2.getText());
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "port should be an integer");
                            isComplete = false;
                        }

                    }
                } else if (guestRadioButton2.isSelected()) {
                    if ((textField1.getText().equals("")) || (textField3.getText().equals("")) || (textField4.getText().equals(""))) {
                        JOptionPane.showMessageDialog(null, "Please fill the blankets");
                        isComplete = false;

                    } else {
                        guest = true;
                        host = false;
                        ip = textField3.getText();
                        clientName = textField1.getText();
                        try {
                            port = Integer.parseInt(textField4.getText());
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "port should be an integer");
                            isComplete = false;
                        }
                    }

                }

                if (isComplete) {

                    setVisible(false);
                    doneChoosing = true;


                }
            }
        }
    }
}




