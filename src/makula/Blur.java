package makula;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Blur extends Filter {

    Blur(MakImage img, double lvl) {
        super(img, lvl);
    }

    @Override
    public void apply() {

        BufferedImage buffImg = image.getBuffImg();
        // Matrix used to create the blur effect - a rectangular array of numbers over our image.
        Kernel kernel = new Kernel(3, 3, new float[]{
                1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f,
                1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f,
                1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f});

        BufferedImageOp op = new ConvolveOp(kernel);
        buffImg = op.filter(buffImg, null);
        image.updateImage(buffImg);
    }
}