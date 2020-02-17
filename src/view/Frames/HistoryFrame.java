package view.Frames;

import model.InputFileReader;
import org.json.JSONArray;
import org.json.JSONObject;
import tools.IButtonListener;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.plaf.LabelUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static view.Frames.SelectModeFrame.scH;
import static view.Frames.SelectModeFrame.scW;

/**
 * Created by Pegah :) on 06/07/2017.
 */
public class HistoryFrame extends JFrame {
    public static Font font = SelectModeFrame.font;
    private JPanel panel;
    private GridBagConstraints gbc;
    private int requestNum = 0;
    private  int fileNumber;
    private  int fileLength;
    private  File[] files;
    private String inputPath;


    public HistoryFrame(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            System.out.println(e);
        }

        setSize((int) (scW / 5), (int) (scH / 1.8));
        setLayout(new BorderLayout());
        setTitle("Conversations");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createPanel();
        readFile();


        setVisible(true);
    }

    public void createPanel(){
        gbc = new GridBagConstraints();
        panel = new JPanel();
        add(panel);

    }

    public void addUsersPanel(String name){
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc1 = new GridBagConstraints();

        JPanel panelU = new JPanel();
        panelU.setBorder(BorderFactory.createLineBorder(Color.black, (int)(scH/400)));
        panelU.setPreferredSize(new Dimension((this.getWidth()- (int)(scW/100)), (int)(scH/15)));
        panelU.setLayout(grid);

        JLabel labelU1 = new JLabel(" " + name);
        labelU1.setFont(new Font("Comic Sans Ms", 1, (int) scH / 80));

        JButton showButton = new JButton("Show History with " + name);
        showButton.setFont(new Font("Comic Sans Ms", 1, (int) scH / 80));
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ConversationFrame conversationFrame = new ConversationFrame(name);
                conversationFrame.setVisible(true);

            }
        });

        //JLabel labelU2 = new JLabel(" " + ip);
        //labelU2.setFont(new Font("Comic Sans Ms", 0, (int) scH / 80));

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

        panelU.add(showButton, gbc1);



        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridx = 0;
        gbc1.gridy = 2;
        gbc1.gridwidth = 1;
        // panelU.add(, gbc1);

        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridx = 1;
        gbc1.gridy = 2;
        gbc1.gridwidth = 1;
        //panelU.add(, gbc1);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = requestNum;
        gbc.gridwidth = 1;
        requestNum++;
        panel.add(panelU ,gbc);
        getContentPane().validate();
        getContentPane().repaint();

    }


    public void readFile(){

        try {
            File f = new File("history");
            fileLength = (int) f.length();

            if (f.isDirectory()) {
                files = f.listFiles();
                fileNumber = files.length;


                for (int i = 0; i < fileNumber; i++) {
                    if (files[i].isFile()) {
                        inputPath = files[i].getName();
                        inputPath = inputPath.replace(".txt", "");
                        addUsersPanel(inputPath);

                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
