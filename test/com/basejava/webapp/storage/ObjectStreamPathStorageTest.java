package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.ObjectSerializer;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR,new ObjectSerializer()));
    }
}