package model;

import java.io.*;

/**
 * Created by Kimiya :) on 09/07/2017.
 */
public class OutputFileWriter {
    private File outputFile;
    private BufferedOutputStream bufferedOutputStream;
    private FileOutputStream streamWriter;

    public OutputFileWriter(String path) {
        outputFile = new File(path);

        try {
            streamWriter = new FileOutputStream(outputFile);
            bufferedOutputStream = new BufferedOutputStream(streamWriter);
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
        }
    }

    public void write(String jsonString) {
        byte[] stringBytes = jsonString.getBytes();
        try {
            bufferedOutputStream.write(stringBytes);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            streamWriter.close();
        } catch (IOException e) {
            System.err.println("problem writing");
        }

    }
}
