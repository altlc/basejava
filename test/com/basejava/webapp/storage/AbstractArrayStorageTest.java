package com.basejava.webapp.storage;

import static org.junit.Assert.*;

public class AbstractArrayStorageTest extends AbstractStorageTest{
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage, AbstractArrayStorage.STORAGE_LIMIT);
    }
}