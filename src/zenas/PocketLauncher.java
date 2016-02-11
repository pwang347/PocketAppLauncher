package zenas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import zenas.util.PreferenceManager;

import java.awt.*;
import java.io.IOException;

//application class
public class PocketLauncher extends Application {
    private Stage primaryStage;
    private MainController control;
    private Parent root;

    public final static String FXML_MAIN = "layout/main.fxml";
    public final static int WIDTH = 400;
    public final static int HEIGHT = 500;

    @Override
    public void start(Stage primaryStage){
        try {
            initFXML();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initStage(primaryStage);
        setLocation();
    }

    //MODIFIES: this
    //EFFECTS:  loads view information from fxml, establishes
    //          controller reference and root listener
    void initFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_MAIN));
        root = loader.load();
        control = loader.getController();
        control.init(this);
        root.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ESCAPE)){
                performExit();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS:  creates a new stage with close behaviour set to
    //          save files to preference
    void initStage(Stage primaryStage){
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setOnCloseRequest(event -> {
            PreferenceManager.clearEntries(PreferenceManager.F_ID);
            PreferenceManager.saveFiles(FileItem.mapFilesToStrings(control.getFilesFromGrid()));
        });
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        /*primaryStage.focusedProperty().addListener((ov, t, t1) -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            primaryStage.close();
        });*/
        this.primaryStage = primaryStage;
    }

    //MODIFIES: this
    //EFFECTS:  creates stage at mouse position
    void setLocation(){
        Point p = MouseInfo.getPointerInfo().getLocation();
        primaryStage.setX(p.getX() - primaryStage.getWidth()/2);
        //Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        //primaryStage.setY(primScreenBounds.getHeight() - primaryStage.getHeight());
        primaryStage.setY(p.getY() - primaryStage.getHeight()/2);
    }

    public static void main(String[] args) {
            launch(args);
    }

    public Stage getStage() {
        return primaryStage;
    }

    //EFFECTS:  closes the stage and exits application
    public void performExit(){
        primaryStage.close();
        System.exit(0);
    }
}
