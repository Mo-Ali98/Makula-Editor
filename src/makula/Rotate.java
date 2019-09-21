package makula;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Rotate extends Effect {

    private MakImage image;

    Rotate(MakImage img) {
        image = img;
    }

    public void apply() {
        BufferedImage buffImg = image.getBuffImg();
        buffImg = rotateImage(buffImg, 90);
        image.updateImage(buffImg);
    }

    public BufferedImage rotateImage(BufferedImage img, double angle) {

        // Calculations needed for rotation
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        // Create graphics
        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        // Transformation for rotation
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);
        int x = w / 2;
        int y = h / 2;

        // Rotate image and draw the rotated version
        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
}