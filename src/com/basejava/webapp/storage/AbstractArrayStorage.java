package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    protected final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int getLimit() {
        return STORAGE_LIMIT;
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume realGet(int index) {
        return storage[index];
    }

    public void realSave(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище переполнено", resume.getUuid());
        }
        addElement(resume);
        size++;
    }

    public void realUpdate(Resume resume, int index) {
        storage[index] = resume;
    }

    public void realDelete(int index) {
        deleteElement(index);
        size--;
        storage[size] = null;
    }

    protected boolean realCheckExists(int index) {
        return index >= 0 && size > 0 && index < size;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void addElement(Resume resume);

    protected abstract void deleteElement(int index);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

}
