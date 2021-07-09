package com.basejava.webapp;

import com.basejava.webapp.storage.SqlStorage;
import com.basejava.webapp.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String PROPS = "resumes.properties";
    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final Storage storageDb;

    private Config() {
        try (InputStream is = Config.class.getResourceAsStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storageDb = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (NullPointerException | IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        } catch (ClassNotFoundException $e) {
            throw new IllegalStateException("Database driver not found");
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorageDb() {
        return storageDb;
    }
}
