package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    public static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume doGet(Object key) {
        return storage[(Integer)key];
    }

    public void doSave(Resume resume, Object key) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище переполнено", resume.getUuid());
        }
        addElement(resume,(Integer)key);
        size++;
    }

    public void doUpdate(Resume resume, Object key) {
        storage[(Integer)key] = resume;
    }

    public void doDelete(Object key) {
        deleteElement((Integer)key);
        size--;
        storage[size] = null;
    }

    protected boolean isExist(Object key) {
        return (Integer)key >= 0;
    }

    protected abstract Object getKey(String uuid);

    protected abstract void addElement(Resume resume, int index);

    protected abstract void deleteElement(int index);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }


}
