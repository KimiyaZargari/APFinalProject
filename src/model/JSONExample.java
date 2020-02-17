package model;

import org.json.JSONArray;
import org.json.JSONObject;
import view.Panels.ChatPanel.ChatMePanel;

/**
 * Created by Kimiya :) on 09/07/2017.
 */
public class JSONExample {
    JSONObject mainObject;
    private JSONArray messageArray;


    public JSONExample() {
        mainObject = new JSONObject();
        messageArray = new JSONArray();
    }

    public void createNewObject(ChatMePanel messagePanel) {
        JSONObject message = new JSONObject();
        message.put("name", messagePanel.getNameLabel().getText());
        message.put("message", messagePanel.getMessageArea().getText());
        message.put("time", messagePanel.getTimeLabel().getText());
        messageArray.put(message);
    }

    public void createJSON() {
        mainObject.put("messages", messageArray);
    }

    @Override
    public String toString() {
        return mainObject.toString();
    }
}
