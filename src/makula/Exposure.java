package makula;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Exposure extends Filter {

    Exposure(MakImage img, double lvl) {
        super(img, lvl);
    }

    @Override
    public void apply() {

        BufferedImage buffImg = image.getBuffImg();

        // Goes through the pixels
        for (int y = 0; y < buffImg.getHeight(); y++) {

            for (int x = 0; x < buffImg.getWidth(); x++) {

                Color c = new Color(buffImg.getRGB(x, y));

                Double a = level / 100.0;

                // Gets value of new RGB values calculated from user input
                int r = Math.toIntExact(Math.round(c.getRed() + ((255 - c.getRed()) * a)));
                int g = Math.toIntExact(Math.round(c.getGreen() + ((255 - c.getGreen()) * a)));
                int b = Math.toIntExact(Math.round(c.getBlue() + ((255 - c.getBlue()) * a)));
                int[] numbers = {r, g, b};

                // Checks if the values go above 255 or below 0.
                for (int i = 0; i < numbers.length; i++) {

                    if (numbers[i] >= 256) {
                        numbers[i] = 255;

                    } else if (numbers[i] < 0) {
                        numbers[i] = 0;
                    }
                }

                buffImg.setRGB(x, y, new Color(numbers[0], numbers[1], numbers[2]).getRGB());
            }
        }
        image.updateImage(buffImg);
    }
}