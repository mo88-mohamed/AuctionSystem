package Helper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Img {
    private Img(){}


    public static Image readImage(String path, Dimension d){
        BufferedImage pic = null;
        try {
            pic = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        java.awt.Image dimg = pic.getScaledInstance(d.width, d.height,
                java.awt.Image.SCALE_SMOOTH);

        return dimg;
    }


    public static ImageIcon createImageIcon(Image img){
        ImageIcon imageIcon = new ImageIcon(img);
        return imageIcon;

    }

}
