package database.core;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import database.core.api.DBManager;
import database.schemas.api.Entry;

public final class LudomaniaDBManager implements DBManager {
    
    private static final String SEP = File.separator;
    private static final LudomaniaDBManager MANAGER = new LudomaniaDBManager();
    
    private LudomaniaDBManager() {}
    
    public static LudomaniaDBManager getManager() {
        return MANAGER;
    }
    
    @Override
    public boolean insert(Entry entry, final String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }
    
    @Override
    public boolean update(Entry entry, final String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
    @Override
    public boolean delete(Entry entry, final String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
    @Override
    public <E> E read(E entry, final String filename) {
        File file = this.findDBFile(filename);
        this.fooRead(file);


        return null;
    }

    private void fooRead(File file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(file);
            String usr = jsonNode.get("username").asText();
            String pwd = jsonNode.get("password").asText();
            System.out.println("Name: " + usr);
            System.out.println("Age: " + pwd);
        } catch (IOException ioEx) {

        }
    }
    
    // private boolean unlockFile(File file) {
    //     return file.setWritable(true);
    // }
    
    // private boolean lockFile(File file) {
    //     return file.setWritable(false);
    // }
    
    private File findDBFile(final String filename) {
        final File currentDir = new File(System.getProperty("user.dir"));        
        File resources = new File(currentDir.getParent() + SEP + "resources");
        
        if (resources.isDirectory()) {
            File dbFile = new File(resources.getPath() + SEP + filename);
            
            if (dbFile.exists()) {
                return dbFile;
            } else {
                this.createDBFile(filename, dbFile);
            }
        } else {
            this.createDBDirectory("resources", resources);
        }

        return this.findDBFile(filename);
    }
    
    private boolean createDBFile(final String filename, File directory) {
        try {
            return new File(directory.getPath() + SEP + filename).createNewFile();
        } catch(IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    
    private boolean createDBDirectory(String dbDirectoryName, File directory) {
        try {
            final File currentDir = new File(System.getProperty("user.dir"));
            return new File(currentDir.getParent() + SEP + "resources").mkdir();
        } catch(SecurityException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    
}
