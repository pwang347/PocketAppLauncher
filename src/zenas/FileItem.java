package zenas;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sun.awt.shell.ShellFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Paul on 1/15/2016.
 */
public class FileItem{
    private AnchorPane root;
    private VBox v;
    private Label l;
    private ImageView i;
    private File file;
    private Controller controller;

    public static final String FXML_URL = "layout/link.fxml";

    public FileItem(File file) throws IOException {
        root = FXMLLoader.load(getClass().getResource(FXML_URL));
        v = (VBox) root.getChildren().get(0);
        i = (ImageView) v.getChildren().get(0);
        l = (Label) v.getChildren().get(1);
        this.file = file;
        setText(file.getName());
        setIcon();
        initListeners();
    }

    public FileItem(File file, Controller controller) throws IOException {
        this(file);
        setController(controller);
    }

    private void initListeners(){
        v.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)){
                controller.removeFileItem(this);
            }
        });
        v.addEventFilter(MouseEvent.MOUSE_RELEASED, event1 ->{
            if(event1.getButton().equals(MouseButton.PRIMARY)) {
                try {
                    Desktop.getDesktop().open(getFile());
                    //callExit();
                } catch (IOException e) {
                    e.printStackTrace();
                    /*if (controller.createDialog(Alert.AlertType.ERROR).showYesNoDialog("Invalid file path", "Would you like to remove the file shortcut?")) {
                        System.out.println("Yes");
                    }*/
                }
            } else {
                event1.consume();
            }
        });
        root.setOnDragDetected(event -> {
            Dragboard db = root.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cc = new ClipboardContent();
            cc.putString(String.valueOf(this));
            db.setContent(cc);
            SnapshotParameters sp = new SnapshotParameters();
            sp.setFill(Color.TRANSPARENT);
            Image img = root.snapshot(sp, null);
            db.setDragView(img, 48, 48);
            root.setVisible(false);
            event.consume();
        });
        root.setOnDragDone(event -> {
            root.setVisible(true);
        });

        /*
        root.setOnDragEntered(event -> {
            if (event.getGestureSource() != root) {
                if(event.getSceneX()-64>root.getLayoutX()) {
                    displaceX(-32, vb);
                } else {
                    displaceX(32, vb);
                }
            }
            event.consume();
        });

        root.setOnDragOver(event -> {
            if (event.getGestureSource() != root) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });

        root.setOnDragExited(event->{
            if (event.getGestureSource() != root) {
                displaceX(0, vb);
            }
            event.consume();
        });

        root.setOnDragDropped(event -> {
            //controller.moveFileItem(this);
        });
        */
        root.setUserData(this);
        //root.getChildren().add(v);
    }

    public void setText(String s){
        l.setText(s);
    }

    public void setIcon(){
        i.setImage(getFileIcon(file));
    }

    private static Image getFileIcon(File file) {
        ShellFolder sf = null;
        try {
            sf = ShellFolder.getShellFolder(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(sf.getIcon(true), sf.getFolderType());
        BufferedImage image = (BufferedImage) icon.getImage();
        Image img = SwingFXUtils.toFXImage(image, null);
        return img;
    }

    public AnchorPane getGraphics(){
        return root;
    }

    public File getFile() {return file;}

    public void setController(Controller c){
        controller = c;
    }

    void callExit(){
        if(controller!=null)
        controller.exit();
    }

    public static java.util.List<String> mapFilesToStrings(java.util.List<File> files){
        java.util.List<String> filePaths = new ArrayList<>();
        for(File f: files){
            filePaths.add(f.getAbsolutePath());
        }
        return filePaths;
    }

    public static java.util.List<File> mapStringsToFiles(java.util.List<String> filePaths){
        java.util.List<File> files = new ArrayList<>();
        for(String f: filePaths){
            files.add(new File(f));
        }
        return files;
    }

    public static String getFileExt(File file){
        String[] split = file.getName().split("\\.(?=[^\\.]+$)");
        return split[split.length-1];
        //return split[split.length-1];
    }
}
