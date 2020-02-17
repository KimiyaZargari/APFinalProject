package view.Frames;

/**
 * Created by Asus on 7/10/2017.
 */

import model.InputFileReader;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static view.Frames.SelectModeFrame.scH;
import static view.Frames.SelectModeFrame.scW;
/**
 * Created by Pegah :) on 06/07/2017.
 */
public class ConversationFrame extends JFrame {
    public static Font font = SelectModeFrame.font;
    private JPanel panel;
    private GridBagConstraints gbc;
    private int requestNum = 0;
    private  int fileNumber;
    private  int fileLength;
    private  File[] files;
    private String inputPath;


    public ConversationFrame(String name){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            System.out.println(e);
        }

        setSize((int) (scW / 5), (int) (scH / 1.8));
        setLayout(new BorderLayout());
        setTitle("Conversation with " + name + ":");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createPanel();

        readJson("history//" + name + ".txt");
        setVisible(true);


    }


    public void readJson(String jFilePath){
        InputFileReader inputFileReader = new InputFileReader(jFilePath);
        String json = inputFileReader.read();
        System.out.println(json);
        json = json.replace("null", "");

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArrayList = jsonObject.getJSONArray("messages");
        String st = "";
        for (int i = 0; i < jsonArrayList.length(); i++) {
            JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
            String namess = jsonObjectList.getString("name");
            String time = jsonObjectList.getString("time");
            String message = jsonObjectList.getString("message");
            addUsersPanel(namess, message, time);

        }

    }

    public void createPanel(){
        gbc = new GridBagConstraints();
        panel = new JPanel();
        add(panel);

    }

    public void addUsersPanel(String mNamess, String mMessage, String mTime){
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc1 = new GridBagConstraints();

        JPanel panelU = new JPanel();
        panelU.setBorder(BorderFactory.createLineBorder(Color.black, (int)(scH/400)));
        panelU.setPreferredSize(new Dimension((this.getWidth()- (int)(scW/100)), (int)(scH/15)));
        panelU.setLayout(grid);

        JLabel labelU1 = new JLabel(" " +mNamess);
        labelU1.setFont(new Font("Comic Sans Ms", 2, (int) scH / 80));


        JLabel labelU2 = new JLabel(" " + mMessage);
        labelU1.setFont(new Font("Comic Sans Ms", 1, (int) scH / 80));


        JLabel labelU3 = new JLabel("      " + mTime);
        labelU1.setFont(new Font("Comic Sans Ms", 2, (int) scH / 80));




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

        panelU.add(labelU2, gbc1);



        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridx = 0;
        gbc1.gridy = 2;
        gbc1.gridwidth = 1;
        panelU.add(labelU3, gbc1);

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

}
