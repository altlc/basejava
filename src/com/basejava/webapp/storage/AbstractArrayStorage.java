package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

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

    public Resume doGet(Integer key) {
        return storage[(Integer)key];
    }

    public void doSave(Resume resume, Integer key) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище переполнено", resume.getUuid());
        }
        addElement(resume,key);
        size++;
    }

    public void doUpdate(Resume resume, Integer key) {
        storage[key] = resume;
    }

    public void doDelete(Integer key) {
        deleteElement(key);
        size--;
        storage[size] = null;
    }

    protected boolean isExist(Integer key) {
        return (Integer)key >= 0;
    }

    protected abstract Integer getKey(String uuid);

    protected abstract void addElement(Resume resume, int index);

    protected abstract void deleteElement(int index);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }


}
