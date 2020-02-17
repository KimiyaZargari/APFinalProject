package model;

import java.io.*;

/**
 * Created by Kimiya :) on 09/07/2017.
 */
public class InputFileReader {
    private File inputFile;
private BufferedInputStream bufferedInputStream;

    public InputFileReader(String path) {
        inputFile = new File(path);
        FileInputStream streamReader;
        try {
            streamReader = new FileInputStream(inputFile);
             bufferedInputStream = new BufferedInputStream(streamReader);
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
        }

    }

    public String read() {
        String jsonString = null;
        try {
            int i;
            while ((i = bufferedInputStream.read()) != -1){
                jsonString += (char) i;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
