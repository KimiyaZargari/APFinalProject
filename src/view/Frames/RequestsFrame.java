package view.Frames;

import tools.IButtonListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static view.Frames.SelectModeFrame.scH;
import static view.Frames.SelectModeFrame.scW;

/**
 * Created by Pegah :) on 06/07/2017.
 */
public class RequestsFrame extends JFrame {
    private IButtonListener iButtonListener;
    public static Font font = SelectModeFrame.font;

    private JPanel panel, panel1;
    private JLabel label1;
    private GridBagConstraints gbc;
    private int requestNum = 1;


    public RequestsFrame(IButtonListener iButtonListener){
        this.iButtonListener = iButtonListener;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            System.out.println(e);
        }

        setSize((int) (scW / 5), (int) (scH / 1.8));
        setLayout(new BorderLayout());
        setTitle("Waiting for connections...");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createPanel();


        setVisible(true);
    }

    public void createPanel(){
        gbc = new GridBagConstraints();

        panel = new JPanel();

        panel1 = new JPanel(new BorderLayout());
        panel1.setBorder(BorderFactory.createLineBorder(Color.black, (int)(scH/400)));
        panel1.setPreferredSize(new Dimension((this.getWidth()- (int)(scW/100)) , (int)(scH/30)));
        label1 = new JLabel(" Received Connections:");
        label1.setFont(new Font("Comic Sans Ms", 1, (int) scH / 80));
        panel1.add(label1, BorderLayout.WEST);

        add(panel);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(panel1, gbc);
    }

    public void addUsersPanel(String name, String ip){
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc1 = new GridBagConstraints();

        JPanel panelU = new JPanel();
        panelU.setBorder(BorderFactory.createLineBorder(Color.black, (int)(scH/400)));
        panelU.setPreferredSize(new Dimension((this.getWidth()- (int)(scW/100)), (int)(scH/15)));
        panelU.setLayout(grid);

        JLabel labelU1 = new JLabel(" " + name);
        labelU1.setFont(new Font("Comic Sans Ms", 1, (int) scH / 80));

        JLabel labelU2 = new JLabel(" " + ip);
        labelU2.setFont(new Font("Comic Sans Ms", 0, (int) scH / 80));

        gbc1.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc1.weightx = 1.0;
        gbc1.weighty= 1.0;
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridwidth = 1;

        panelU.add(labelU1,gbc1);

        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridx = 0;
        gbc1.gridy = 1;
        gbc1.gridwidth = 1;

        panelU.add(labelU2, gbc1);

        JButton rejectButton = new JButton("Reject");
        rejectButton.setFont(new Font("Comic Sans Ms", 1, (int) scH / 80));
        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iButtonListener.onRejectClicked(ip);
                //removeUsersPanel(ip);

            }
        });

        JButton acceptButton = new JButton("Accept");
        acceptButton.setFont(new Font("Comic Sans Ms", 1, (int) scH / 80));
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               iButtonListener.onAcceptClicked(ip);
            }
        });

        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridx = 0;
        gbc1.gridy = 2;
        gbc1.gridwidth = 1;
        panelU.add(rejectButton, gbc1);

        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridx = 1;
        gbc1.gridy = 2;
        gbc1.gridwidth = 1;
        panelU.add(acceptButton, gbc1);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = requestNum;
        gbc.gridwidth = 1;
        requestNum++;
        panel.add(panelU ,gbc);
        getContentPane().validate();
        getContentPane().repaint();
    }

    public void removeUsersPanel(String str){
        for (int i = 1; i < panel.getComponentCount() ; i++) {
            JPanel tmpPanel = (JPanel) panel.getComponent(i);
            JLabel tmpLabel = (JLabel) tmpPanel.getComponent(1);
            if(tmpLabel.getText().equals(" " + str)){
                panel.remove(i);
                //  if( i == (panel.getComponentCount() - 1) )
                repaint();
                requestNum--;
                setVisible(true);
            }
        }
    }


}
