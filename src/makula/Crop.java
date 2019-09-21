package makula;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Crop extends Effect {

    private int x;
    private int y;
    private int width;
    private int height;

    Crop(MakImage img, int x, int y, int w, int h) {
        super(img);
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public void apply() {
        BufferedImage buffImg = image.getBuffImg();
        buffImg = cropImage(buffImg, x, y, width, height);
        image.updateImage(buffImg);
    }

    public BufferedImage cropImage(BufferedImage img, int x, int y, int w, int h) {

        img = img.getSubimage(x, y, w - x, h - y); // Creates a subimage edited off the original

        // Draws the cropped image
        BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB); // Makes a copy of the subimage
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, null);

        return copyOfImage;
    }
}