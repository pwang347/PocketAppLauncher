package zenas.util;

import zenas.PocketLauncher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Created by Paul on 1/27/2016.
 */

//utility class that provides static storage and retrieval of preferences
public class PreferenceManager {
    public static final String F_ID = "f";
    public static Preferences prefs =  Preferences.userRoot().node(PocketLauncher.class.getName());

    //EFFECTS:  saves a list of filepaths at F_ID
    public static void saveFiles(List<String> files){
        for(int i = 0; i<files.size(); i++){
            System.out.println("Saved " + files.get(i) + " as f" +i);
            prefs.put(F_ID+i, files.get(i));
        }
    }

    //EFFECTS:  gets a list of files at F_ID
    public static List<File> getFiles(){
        System.out.println("Number of objects to get: " + getEntrySize(F_ID));
        List<File> files = new ArrayList<>();
        for(int i = 0; i<getEntrySize(F_ID); i++){
            System.out.println("Loaded " + (prefs.get(F_ID+i, null)));
            files.add(new File(prefs.get(F_ID+i, null)));
        }
        return files;
    }

    //EFFECTS:  counts the number of items stored at given id
    public static int getEntrySize(String ID){
        int count = 0;
        while(true){
            String s = prefs.get(ID+count, null);
            if(s==null){
                break;
            }
            count++;
        }
        return count;
    }

    //EFFECTS:  removes preferences at given ID
    public static void clearEntries(String ID){
        int size = getEntrySize(ID);
        System.out.println("Number of objects to remove: " + size);
        for(int i = 0; i<size; i++){
            System.out.println("Removed " + ID + i);
            prefs.remove(ID+i);
        }
    }

}
