package makula;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Sharpen extends Filter {

    Sharpen(MakImage img, double lvl) {
        super(img, lvl);
    }

    @Override
    public void apply() {

        BufferedImage buffImg = image.getBuffImg();
        // Matrix used to create the sharpen effect - a rectangular array of numbers over our image.
        Kernel kernel = new Kernel(3, 3, new float[]{
                0.0f, -0.2f, 0.0f,
                -0.2f, 1.8f, -0.2f,
                0.0f, -0.2f, 0.0f});

        BufferedImageOp op = new ConvolveOp(kernel);
        buffImg = op.filter(buffImg, null);
        image.updateImage(buffImg);
    }
}