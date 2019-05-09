package clue.view.viewController;

import clue.model.ClueText;
import clue.model.board.Grid;
import clue.view.Clue;
import clue.view.navigation.NavigationController;
import clue.view.navigation.PaneSelector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

/**
 * This is the controller for the settings fxml file where all widow settings are inputted to change how to window looks
 */
public class SettingsMenuController {
    private ClueText clue = Clue.getClue();
    private Grid grid = Clue.getGrid();

    //Settings Menu
    @FXML
    private ToggleButton settingsMenuSound;
    @FXML
    private ToggleButton settingsMenuFullscreen;
    @FXML
    private Button settingsMenuBack;

    private static void fullscreen() {
        if (!Clue.getPrimaryStage().isFullScreen()) {
            letterbox(Clue.getPrimaryStage().getScene(), Clue.getRootLayout());
            Clue.getPrimaryStage().setFullScreen(true);
        } else {
            letterbox(Clue.getRootLayout().getScene(), Clue.getRootLayout());
            Clue.getPrimaryStage().setFullScreen(false);
        }
    }

    private static void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);

    }

    @FXML
    private void handleSettingsMenuFullscreen() {
        fullscreen();
    }

    @FXML
    private void handleSettingsMenuSound() {
        //TODO When sound is added add a mute for all sound.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @FXML
    private void handleSettingsMenuBack() {
        NavigationController.loadPane(PaneSelector.MENU);
    }

    public void print(Object s) {
        System.out.println(s);
    }

    private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;

        SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
            this.scene = scene;
            this.ratio = ratio;
            this.initHeight = initHeight;
            this.initWidth = initWidth;
            this.contentPane = contentPane;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            final double newWidth = scene.getWidth();
            final double newHeight = scene.getHeight();

            double scaleFactor =
                    newWidth / newHeight > ratio
                            ? newHeight / initHeight
                            : newWidth / initWidth;

            if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);

                contentPane.setPrefWidth(newWidth / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);
            } else {
                contentPane.setPrefWidth(Math.max(initWidth, newWidth));
                contentPane.setPrefHeight(Math.max(initHeight, newHeight));
            }
        }
    }
}
