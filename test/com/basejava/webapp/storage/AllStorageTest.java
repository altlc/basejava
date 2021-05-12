package com.basejava.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ArrayStorageTest.class,
                SortedArrayStorageTest.class,
                ListStorageTest.class,
                MapStorageByUuidTest.class,
                MapStorageByResumeTest.class,
                ObjectStreamFileStorageTest.class,
                ObjectStreamPathStorageTest.class,
                XmlPathStorageTest.class,
                JsonPathStorageTest.class,
                DataPathStorageTest.class
        })
public class AllStorageTest {
}