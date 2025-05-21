package database.models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import database.models.api.DBModel;
import database.schemas.WalletEntry;
import database.schemas.api.Entry;

public final class Wallet implements DBModel {
    private final String dbFilename = "wallets.json";
    private final WalletEntry entry;
    
    public Wallet(final WalletEntry entry) {
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
    public Optional<WalletEntry> read() {
        return Manager.getManager().read(entry, this.dbFilename);
    }
    
    @Override
    public Optional<List<WalletEntry>> readAll() {
        return Manager.getManager().readAll(this.dbFilename);
    }

    private final static class Manager {    
        private static final String SEP = File.separator;
        private static final String DB_DIRECTORY_NAME = "resources";
        private static final String DB_DIRECTORY_PATH = 
        new File(System.getProperty("user.dir")).getPath() + SEP + "src" + SEP + "main" + SEP + "java" + SEP + "database" + SEP + DB_DIRECTORY_NAME;
        
        private static final Manager MANAGER = new Manager();
        
        private Manager() { }
        
        private static Manager getManager() {
            return MANAGER;
        }    
        
        private boolean write(final WalletEntry entry, final String filename) {
            boolean result;
            
            try {
                File file = this.findDBFile(filename);
                
                ObjectMapper objectMapper = new ObjectMapper();
                
                Optional<List<WalletEntry>> list = this.readAll(filename);
                ArrayList<WalletEntry> entries = list.isEmpty() ? new ArrayList<>() : new ArrayList<>(list.get());

                entries.removeIf(e -> e.getIdentifier() == entry.getIdentifier());
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
        
        private boolean delete(final WalletEntry entry, final String filename) {
            boolean result;        
            File file;
            try {
                file = this.findDBFile(filename);
                
                ObjectMapper objectMapper = new ObjectMapper();
                
                Optional<List<WalletEntry>> list = this.readAll(filename);
                List<WalletEntry> entries = list.isEmpty() ? Arrays.asList() : list.get();
                
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
        
        private Optional<WalletEntry> read(final WalletEntry entry, final String filename) {
            Optional<WalletEntry> result = Optional.empty();
            try {
                File file = this.findDBFile(filename);
                ObjectMapper objectMapper = new ObjectMapper();
                
                // Read the JSON array into a List of User objects
                List<WalletEntry> entries = objectMapper.readValue(file, new TypeReference<List<WalletEntry>>() {});
                
                result = entries.stream().filter(u -> u.getIdentifier() == entry.getIdentifier()).findFirst();
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
        
        private Optional<List<WalletEntry>> readAll(final String filename) {
            Optional<List<WalletEntry>> result = Optional.empty();
            try {
                File file = this.findDBFile(filename);
                ObjectMapper objectMapper = new ObjectMapper();
                
                // Read the JSON array into a List of User objects
                result = objectMapper.readValue(file, new TypeReference<List<WalletEntry>>() {});            
                
                System.out.println("List of Users:");
                for (Entry user : result.get()) {
                    System.out.println(user);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            
            return result;
        }
        
        private boolean exists(final Entry entry, final String filename) {
            boolean result = false;
            try {
                File file = this.findDBFile(filename);
                ObjectMapper objectMapper = new ObjectMapper();
                
                // Read the JSON array into a List of User objects
                List<WalletEntry> entries = objectMapper.readValue(file, new TypeReference<List<WalletEntry>>() {});
                
                result = entries.stream().anyMatch(e -> e.getIdentifier() == entry.getIdentifier());
            } catch (Exception e) {
                System.err.println(e.getMessage());
                result = false;
            }
            
            return result;
        }
        
        private File findDBFile(final String filename) throws Exception {       
            File resources = new File(DB_DIRECTORY_PATH);
            
            if (resources.isDirectory()) {
                File dbFile = new File(resources.getPath() + SEP + filename);
                
                if (dbFile.exists()) {
                    return dbFile;
                } else {
                    if (!this.createDBFile(filename, dbFile)) {
                        throw new Exception("cannot create db file:" + filename);
                    }
                }
            } else {
                if (!this.createDBDirectory(DB_DIRECTORY_NAME, resources)) {
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
        
        private boolean createDBDirectory(final String dbDirectoryName, File directory) {
            try {
                return new File(DB_DIRECTORY_PATH).mkdir();
            } catch(SecurityException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }
        
    }
}


