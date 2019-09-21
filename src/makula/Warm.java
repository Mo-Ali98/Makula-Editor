package makula;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Warm extends Filter {

    public Warm(MakImage img, double lev) {
        super(img, lev);
    }

    @Override
    public void apply() {

        BufferedImage buffImg = image.getBuffImg();

        // Gets the image width and height from makImage
        int width = buffImg.getWidth();
        int height = buffImg.getHeight();

        // Loop through the image
        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                Color pixelColor = new Color(buffImg.getRGB(x, y), true); // Gets the color of each pixel
                // Manipulate RGB values
                int newR = (int) (pixelColor.getRed() * .8);
                int newG = (int) (pixelColor.getGreen() * 0.7);
                int newB = (int) (pixelColor.getBlue() * 0.6);
                int a = pixelColor.getAlpha();

                // Set the new RGB
                int rgba = (a << 24) | (newR << 16) | (newG << 8) | newB;
                buffImg.setRGB(x, y, rgba);
            }
        }
        this.image.updateImage(buffImg);
    }
}