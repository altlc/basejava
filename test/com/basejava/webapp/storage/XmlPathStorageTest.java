package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.XmlSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR,new XmlSerializer()));
    }
}