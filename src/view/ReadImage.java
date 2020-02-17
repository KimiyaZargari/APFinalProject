package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Kimiya :) on 08/07/2017.
 */
public class ReadImage {
    public static Image readImage(String filePath, int w, int h) {
        BufferedImage image = new BufferedImage(100, 100, 1);
        try {
            File f = new File(filePath);
            image = ImageIO.read(f);

        } catch (IOException e) {
            System.out.println(e);
        }
        Image resizedImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return resizedImage;
    }

    public static ImageIcon makeIcon(String filePath, int w, int h){
        ImageIcon icon = new ImageIcon(readImage(filePath,w,h));
        return icon;
    }
}
