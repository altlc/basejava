package com.basejava.webapp.storage;

public class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage, AbstractArrayStorage.STORAGE_LIMIT);
    }
}