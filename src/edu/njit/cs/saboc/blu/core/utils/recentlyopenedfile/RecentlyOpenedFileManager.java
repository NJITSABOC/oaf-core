package edu.njit.cs.saboc.blu.core.utils.recentlyopenedfile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Chris O
 */
public class RecentlyOpenedFileManager {
    
    public static class RecentlyOpenedFileException extends Exception {
        public RecentlyOpenedFileException(String message) {
            super(message);
        }
    }
    
    private final Class<?> prefsClazz; 
    private final String fileTypeKey;
    
    private final ArrayList<RecentlyOpenedFile> recentlyOpenedFiles;
    
    public RecentlyOpenedFileManager(Class<?> prefsClazz, String fileTypeKey) {
        this.prefsClazz = prefsClazz;
        this.fileTypeKey = fileTypeKey;
        
        this.recentlyOpenedFiles = new ArrayList<>();
        
        try {
            this.recentlyOpenedFiles.addAll(loadRecentlyOpened());
        } catch (RecentlyOpenedFileException rofe) {
            
        }
    }
    
    public ArrayList<RecentlyOpenedFile> getRecentlyOpenedFiles() {
        return this.recentlyOpenedFiles;
    }
    
    public ArrayList<RecentlyOpenedFile> getRecentlyOpenedFiles(int maxCount) {
        int lastIndex = Math.min(maxCount, recentlyOpenedFiles.size());
        
        return new ArrayList<>(recentlyOpenedFiles.subList(0, lastIndex));
    }
    
    private ArrayList<RecentlyOpenedFile> loadRecentlyOpened() throws RecentlyOpenedFileException {
        Preferences prefsRoot = Preferences.userNodeForPackage(prefsClazz);

        String recentlyOpenedFilesStr = prefsRoot.get(fileTypeKey, "");

        if (recentlyOpenedFilesStr.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            JSONParser parser = new JSONParser();
            JSONArray recentlyOpenedFilesJSON = (JSONArray) parser.parse(recentlyOpenedFilesStr);
            
            ArrayList<RecentlyOpenedFile> result = new ArrayList<>();
            
            for(Object obj : recentlyOpenedFilesJSON) {
                JSONObject jsonObj = (JSONObject)obj;
                
                String pathStr = jsonObj.get("path").toString();
                String dateStr = jsonObj.get("date").toString();
                
                File file = new File(pathStr);
                Date date = new Date(Long.parseLong(dateStr));
                
                if(file.exists()) {
                    result.add(new RecentlyOpenedFile(file, date));
                }
            }

            result.sort((a, b) -> a.getDate().compareTo(b.getDate()));

            return result;
            
        } catch (ParseException pe) {
            throw new RecentlyOpenedFileException("Error parsing recently opened files list.");
        }
    }
    
    public void eraseHistory() {
        Preferences prefsRoot = Preferences.userNodeForPackage(prefsClazz);
        prefsRoot.remove(fileTypeKey);
        
        recentlyOpenedFiles.clear();
    }
    
    public void addOrUpdateRecentlyOpenedFile(File file) throws RecentlyOpenedFileException {
        Preferences prefsRoot = Preferences.userNodeForPackage(prefsClazz);

        ArrayList<RecentlyOpenedFile> recentFiles = this.getRecentlyOpenedFiles();

        Optional<RecentlyOpenedFile> existingFile = recentFiles.stream().filter( (recentlyOpenedFile) -> {
            return recentlyOpenedFile.getFile().equals(file);
        }).findAny();

        if(existingFile.isPresent()) {
            existingFile.get().updateLastOpened(new Date());
        } else {
            recentFiles.add(new RecentlyOpenedFile(file, new Date()));
        }
        
        recentFiles.sort((a, b) -> a.getDate().compareTo(b.getDate()));
  
        prefsRoot.put(fileTypeKey, JSONArray.toJSONString(this.toJSON(recentFiles)));
    }
    
    private JSONArray toJSON(List<RecentlyOpenedFile> files) {
        JSONArray result = new JSONArray();
        
        files.forEach( (file) -> {
            result.add(file.toJSON());
        });
        
        return result;
    }
}
