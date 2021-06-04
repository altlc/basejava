package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.ObjectSerializer;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR.getAbsolutePath(),new ObjectSerializer()));
    }
}