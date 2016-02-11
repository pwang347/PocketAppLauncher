package zenas;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import zenas.util.Pair;
import zenas.util.PreferenceManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//FXML controller for main view
public class MainController extends Controller {
    private PocketLauncher pocketLauncher;
    public GridPane container;
    public TextField searchField;

    private List<File> fileList;
    private List<File> selectedFileList;

    public static final int MAX_ROWS = 4;
    public static final int MAX_COLS = 4;

    //MODIFIES: this
    //EFFECTS:  initializes app reference and listeners,
    //          then loads and displays files
    @Override
    public void init(PocketLauncher pocketLauncher){
        super.init(pocketLauncher);
        loadFiles();
    }

    //MODIFIES: this
    //EFFECTS:  establishes drag behaviour for grid container and
    //          text-change behaviour for the search text field
    @Override
    void initListeners() {
        container.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if(db.hasFiles()){
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        });
        container.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if(db.hasFiles()){
                success = true;
                db.getFiles().stream().filter(f -> !fileList.contains(f)).forEach(f -> {
                    fileList.add(f);
                    addFileToGrid(f);
                });
            }
            event.setDropCompleted(success);
            event.consume();
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterFiles(newValue);
        });
    }

    //EFFECTS:  saves current file list to preferences and schedules exit
    @Override
    public void exit() {
        PreferenceManager.saveFiles(FileItem.mapFilesToStrings(getFilesFromGrid()));
        super.exit();
    }

    //MODIFIES: this
    //EFFECTS:  loads files from preferences and displays them
    void loadFiles(){
        fileList = new ArrayList<>(PreferenceManager.getFiles());
        selectedFileList = new ArrayList<>(fileList);
        populateGrid(selectedFileList);
    }

    //MODIFIES: this
    //EFFECTS:  filters files with current text field text based on name
    //          or file extension
    void filterFiles(String query){
        selectedFileList.clear();
        for(File f: fileList){
            if(f.getName().startsWith(query)||query.equals(FileItem.getFileExt(f))){
               selectedFileList.add(f);
            }
        }
        populateGrid(selectedFileList);
    }

    //MODIFIES: this
    //EFFECTS:  creates a new FileItem and adds it to the grid container
    void addFileToGrid(File f){
        try {
            FileItem fi = new FileItem(f, this);
            Pair<Integer,Integer> rowCol = getRowCol(container.getChildren().size(), MAX_ROWS, MAX_COLS);
            container.add(fi.getGraphics(), rowCol.first(), rowCol.second());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //REQUIRES: size, maxRows and maxCols are > 0
    //EFFECTS:  creates a new pair object of the form <row, col>
    Pair<Integer, Integer> getRowCol(int size, int maxRows, int maxCols){
        return new Pair<Integer, Integer>(size%maxRows, size/maxCols);
    }

    //EFFECTS:  returns the list of files stored as FileItems in the
    //          grid container
    List<File> getFilesFromGrid(){
        List<File> files = new ArrayList<>();
        for(Node root : container.getChildren()){
            FileItem fi = (FileItem) root.getUserData();
            files.add(fi.getFile());
        }
        return files;
    }

    //MODIFIES: this
    //EFFECTS:  clears the items in the grid container, and then
    //          adds FileItems corresponding to supplied file list
    void populateGrid(List<File> files){
        container.getChildren().clear();
        for(File f : files){
            addFileToGrid(f);
        }
    }

    public List<File> getFiles(){
        return fileList;
    }

    //REQUIRES: FileItem exists
    //MODIFIES: this
    //EFFECTS:  removes a file item, and populates grid again
    @Override
    public void removeFileItem(FileItem fi){
        fileList.remove(fi.getFile());
        filterFiles(searchField.getText());
    }

}
