package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.JsonSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR,new JsonSerializer()));
    }
}