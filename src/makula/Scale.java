package makula;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Scale extends Effect {

    public int amount;

    Scale(MakImage img, int am) {
        super(img);
        amount = am;
    }

    public void apply() {
        BufferedImage buffImg = image.getBuffImg();
        buffImg = scaleImage(buffImg, amount);
        image.updateImage(buffImg);
    }

    public static BufferedImage scaleImage(BufferedImage img, int amount) {

        // New measurements of image
        int newWidth = (int) (img.getWidth() * (1 + amount / 100.0));
        int newHeight = (int) (img.getHeight() * (1 + amount / 100.0));

        // Draws the scaled image
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(img, 0, 0, newWidth, newHeight, null);

        return newImage;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}