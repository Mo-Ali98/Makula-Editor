package makula;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MakImage {

    private int width;
    private int height;
    private BufferedImage BuffImg;
    private Image DisplayImg;
    private ActionManager actionManager;

    MakImage(int w, int h, BufferedImage buffImg, Image img, ActionManager a) {
        setWidth(w);
        setHeight(h);
        setBuffImg(buffImg);
        setDisplayImg(img);
        setActionManager(a);
    }

    MakImage(String src) {
        // Gets file from src path
        File originalImage = new File(src);
        setDisplayImg(new Image("file:" + src));

        try {
            setBuffImg(ImageIO.read(originalImage));
            setWidth(BuffImg.getWidth());
            setHeight(BuffImg.getHeight());
            setActionManager(new ActionManager(this));
        } catch (IOException e) {
            System.out.print("Image failed to be imported");
        }
    }

    public void updateImage(BufferedImage image) {
        setBuffImg(image);
        setDisplayImg(SwingFXUtils.toFXImage(image, null));
        setWidth(image.getWidth());
        setHeight(image.getHeight());
        actionManager.update(this);
    }

    public void undo() {
        actionManager.undo();
        if (actionManager.getState() != null) {
            reassign(actionManager.getState());
        }
    }

    public void reassign(MakImage img) {
        setWidth(img.getWidth());
        setHeight(img.getHeight());
        setBuffImg(img.getBuffImg());
        setDisplayImg(img.getDisplayImg());
    }

    public MakImage cloneMakImg() {
        return new MakImage(this.getWidth(),
                this.getHeight(),
                this.getBuffImg(),
                this.getDisplayImg(),
                this.getActionManager());
    }

    // Getters and Setters
    public int getWidth() {
        return width;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getBuffImg() {
        return BuffImg;
    }

    private void setBuffImg(BufferedImage buffImg) {
        this.BuffImg = buffImg;
    }

    public Image getDisplayImg() {
        return DisplayImg;
    }

    private void setDisplayImg(Image displayImg) {
        this.DisplayImg = displayImg;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public void setActionManager(ActionManager actionManager) {
        this.actionManager = actionManager;
    }
}