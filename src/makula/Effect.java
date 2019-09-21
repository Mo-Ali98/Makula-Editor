package makula;

public class Effect implements EffectInterface {

    protected MakImage image;

    public Effect(MakImage img) {
        image = img;
    }

    public Effect() {
    }

    public void apply() {
        return;
    }

    public void setImage(MakImage img) {
        this.image = img;
    }
}