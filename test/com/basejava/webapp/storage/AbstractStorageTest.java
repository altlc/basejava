package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.basejava.webapp.ResumeTestData.generateResume;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = ".\\storage\\";

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String DUMMY_UUID = "dummy";
    private static final Resume RESUME_1 = generateResume(UUID_1,"TEST1");
    private static final Resume RESUME_2 = generateResume(UUID_2,"TEST2");
    private static final Resume RESUME_3 = generateResume(UUID_3,"TEST3");
    private static final Resume RESUME_4 = generateResume(UUID_4,"TEST4");
    private static final Resume DUMMY_RESUME = generateResume(DUMMY_UUID,"DUMMY");
    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(DUMMY_UUID);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExists() {
        storage.save(RESUME_2);
    }

    @Test
    public void update() {
        storage.update(RESUME_2);
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNonExists() {
        storage.update(DUMMY_RESUME);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNonExists() {
        storage.delete(DUMMY_UUID);
    }

    @Test
    public void getAllSorted() {
        List<Resume> actual = storage.getAllSorted();
        List<Resume> expected = Arrays.asList(RESUME_1,RESUME_2,RESUME_3);

        Assert.assertEquals(3, actual.size());
        Assert.assertEquals(expected,actual);
    }
}