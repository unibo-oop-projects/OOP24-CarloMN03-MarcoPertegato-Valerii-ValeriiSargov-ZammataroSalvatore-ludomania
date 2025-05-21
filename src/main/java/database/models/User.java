package database.models;

import java.util.List;
import java.util.Optional;

import database.models.api.DBModel;
import database.schemas.UserEntry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import database.schemas.api.Entry;

public final class User implements DBModel {
    private final String dbFilename = "users.json";
    private final UserEntry entry;
    
    public User(final UserEntry entry) {
        this.entry = entry;
    }
    
    @Override
    public boolean exists() {
        return Manager.getManager().exists(entry, this.dbFilename);
    }
    
    @Override
    public boolean write() {
        return Manager.getManager().write(entry, this.dbFilename);
    }
    
    @Override
    public boolean delete() {
        return Manager.getManager().delete(entry, this.dbFilename);
    }
    
    @Override
    public Optional<UserEntry> read() {
        return Manager.getManager().read(entry, this.dbFilename);
    }
    
    @Override
    public Optional<List<UserEntry>> readAll() throws Exception {
        return Manager.getManager().readAll(this.dbFilename);
    }
    
    
    private final static class Manager {
        private static final String SEP = File.separator;
        private static final String DB_DIRECTORY_NAME = "resources";
        private static final String DB_DIRECTORY_PATH = 
        new File(System.getProperty("user.dir")).getPath() + SEP + "src" + SEP + "main" + SEP + "java" + SEP + "database" + SEP + DB_DIRECTORY_NAME;
        
        private static final Manager MANAGER = new Manager();
        
        private Manager() {}
        
        private static Manager getManager() {
            return MANAGER;
        }    
        
        private boolean write(UserEntry entry, String filename) {
            boolean result;
            
            try {
                File file = this.findDBFile(filename);
                
                ObjectMapper objectMapper = new ObjectMapper();
                
                Optional<List<UserEntry>> list = this.readAll(filename);
                ArrayList<UserEntry> entries = list.isEmpty() ? new ArrayList<>() : new ArrayList<>(list.get());
                
                entries.removeIf(e -> e.identifier().equals(entry.identifier()));
                entries.add(entry);
                
                objectMapper.writeValue(file, entries);
                
                System.out.println("List of users written to " + filename);
                
                result = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                result = false;
            }
            
            return result;
        }
        
        private boolean delete(UserEntry entry, final String filename) {
            boolean result;        
            File file;
            try {
                file = this.findDBFile(filename);
                
                ObjectMapper objectMapper = new ObjectMapper();
                
                Optional<List<UserEntry>> list = this.readAll(filename);
                List<UserEntry> entries = list.isEmpty() ? Arrays.asList() : list.get();
                
                entries.removeIf(null);
                
                objectMapper.writeValue(file, entries);
                
                System.out.println("List of users written to " + filename);
                
                result = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                result = false;
            }
            
            return result;
        }
        
        private Optional<UserEntry> read(UserEntry entry, final String filename) {
            Optional<UserEntry> result = Optional.empty();
            try {
                File file = this.findDBFile(filename);
                ObjectMapper objectMapper = new ObjectMapper();
                
                // Read the JSON array into a List of User objects
                List<UserEntry> entries = objectMapper.readValue(file, new TypeReference<List<UserEntry>>() {});
                
                result = entries.stream().filter(u -> u.identifier() == entry.identifier()).findFirst();
                if (result.isPresent()) {
                    System.out.println(result);
                }
                
                System.out.println("List of Users:");
                for (Entry user : entries) {
                    System.out.println(user);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            
            return result;
        }
        
        private Optional<List<UserEntry>> readAll(String filename) throws Exception {
            List<UserEntry> result = new ArrayList<>();
            try {
                File file = this.findDBFile(filename);
                ObjectMapper objectMapper = new ObjectMapper();
                
                // Read the JSON array into a List of User objects
                result = objectMapper.readValue(file, new TypeReference<List<UserEntry>>() {});                      
            } catch (final JsonMappingException e) {
                System.err.println(e.getMessage());
                result = new ArrayList<>();
            }
            
            return Optional.of(result);
        }
        
        private boolean exists(Entry entry, String filename) {
            boolean result = false;
            try {
                File file = this.findDBFile(filename);
                ObjectMapper objectMapper = new ObjectMapper();
                
                // Read the JSON array into a List of User objects
                List<UserEntry> entries = objectMapper.readValue(file, new TypeReference<List<UserEntry>>() {});
                
                result = entries.stream().anyMatch(e -> e.identifier() == entry.identifier());
            } catch (Exception e) {
                System.err.println(e.getMessage());
                result = false;
            }
            
            return result;
        }
        
        private File findDBFile(final String filename) throws Exception {       
            File dbDirectory = new File(DB_DIRECTORY_PATH);
            
            if (dbDirectory.isDirectory()) {
                File dbFile = new File(dbDirectory.getPath() + SEP + filename);
                
                if (dbFile.exists()) {
                    return dbFile;
                } else {
                    if (!this.createDBFile(filename, dbDirectory)) {
                        throw new Exception("cannot create db file:" + filename);
                    }
                }
            } else {
                if (!this.createDBDirectory(dbDirectory)) {
                    throw new Exception("cannot create db directory");
                }
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
        
        private boolean createDBDirectory(final File directory) {
            try {
                return new File(DB_DIRECTORY_PATH).mkdir();
            } catch (final SecurityException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }
    }
    
}
