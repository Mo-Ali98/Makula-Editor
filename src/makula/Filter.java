package makula;

public class Filter extends Effect {

    protected double level;

    public Filter(MakImage img, double lev) {
        super(img);
        level = lev;
    }

    public void apply() {
        return;
    }

    public void setLevel(double level) {
        this.level = level;
    }
}