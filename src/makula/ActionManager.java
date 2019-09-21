package makula;

import javafx.scene.control.Alert;

import java.util.Stack;

public class ActionManager {

    public Stack<MakImage> stateStack = new Stack<>();

    ActionManager(MakImage image) {
        stateStack.push(image.cloneMakImg());
    }

    public void undo() {

        if (stateStack.size() > 1) {
            stateStack.pop();

        } else { // If there is no effect left applied on the image, display warning.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Cannot undo");
            alert.showAndWait();
        }
    }

    public void update(MakImage img) {
        stateStack.push(img.cloneMakImg());
    }

    public MakImage getState() {
        if (stateStack.size() > 0) {
            return stateStack.lastElement();
        }
        return null;
    }

    public Stack<MakImage> getStateStack() {
        return stateStack;
    }

    public void setStateStack(Stack<MakImage> stateStack) {
        this.stateStack = stateStack;
    }
}