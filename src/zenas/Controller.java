package zenas;

import javafx.scene.control.Alert;
import zenas.util.DialogBuilder;

/**
 * Created by Paul on 1/25/2016.
 */

//abstract FXML controller class
public abstract class Controller {
    PocketLauncher pocketLauncher;

    //MODIFIES: this
    //EFFECTS:  initializes app reference and listeners,
    //          then loads and displays files
    public void init(PocketLauncher pocketLauncher){
        this.pocketLauncher = pocketLauncher;
        initListeners();
    }

    //MODIFIES: this
    //EFFECTS:  sets up listener behaviours
    abstract void initListeners();

    //EFFECTS:  creates a generic dialog builder object
    public DialogBuilder createDialog(Alert.AlertType alertType) {
        return new DialogBuilder(pocketLauncher, alertType);
    }

    public PocketLauncher getApplication() {
        return pocketLauncher;
    }

    //EFFECTS:  schedules for exit
    public void exit(){
        pocketLauncher.performExit();
    }

    //REQUIRES: FileItem exists
    //MODIFIES: this
    //EFFECTS:  removes a file item
    abstract void removeFileItem(FileItem fi);
}
