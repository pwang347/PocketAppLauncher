package zenas.util;

/**
 * Created by Paul on 12/24/2015.
 */

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import zenas.PocketLauncher;

/**
 * Created by Paul on 8/31/2015.
 */

//helper class for displaying dialogs
public class DialogBuilder {
    Alert.AlertType alertType;
    PocketLauncher pocketLauncher;

    //MODIFIES: this
    //EFFECTS:  creates a new DialogBuilder object
    public DialogBuilder(PocketLauncher pocketLauncher, Alert.AlertType alertType) {
        this.alertType = alertType;
        this.pocketLauncher = pocketLauncher;
    }

    //EFFECTS:  returns a new generic alert
    Alert template(String title, String text) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(pocketLauncher.getStage());
        alert.setGraphic(null);
        alert.setContentText(text);
        return alert;
    }

    //EFFECTS:  creates a generic dialog with title and text,
    //          shows it and then returns it
    public Alert showDialog(String title, String text) {
        Alert alert = template(title, text);
        alert.showAndWait();
        return alert;
    }


    //EFFECTS:  creates generic dialog and displays it, offers
    //          additional customization
    public Alert showDialog(String title, String text, String header, Node graphic) {
        Alert alert = template(title, text);
        alert.setHeaderText(header);
        alert.setGraphic(graphic);
        alert.showAndWait();
        return alert;
    }

    //EFFECTS:  creates a generic yesno dialog and returns result
    public boolean showYesNoDialog(String title, String text) {
        Alert alert = template(title, text);
        ButtonType buttonYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        alert.showAndWait();
        return alert.getResult().equals(buttonYes);
    }

    //EFFECTS:  returns a generic text input dialog
    TextInputDialog textTemplate(String title, String text){
        TextInputDialog dialog = new TextInputDialog(null);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(text);
        dialog.initOwner(pocketLauncher.getStage());
        return dialog;
    }

    //EFFECTS:  creates a generic text input dialog, and returns result
    public String showTextDialog(String title, String text) {
        TextInputDialog dialog = textTemplate(title, text);
        dialog.showAndWait();
        return dialog.getResult();
    }

    //EFFECTS:  creates a generic text input dialog with more options
    public String showTextDialog(String title, String text, String header) {
        TextInputDialog dialog = textTemplate(title, text);
        dialog.setHeaderText(header);
        dialog.showAndWait();
        return dialog.getResult();
    }
}

