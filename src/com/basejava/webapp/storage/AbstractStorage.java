package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected abstract Object getKey(String uuid);

    protected abstract Resume doGet(Object key);

    protected abstract void doUpdate(Resume r, Object key);

    protected abstract void doSave(Resume r, Object key);

    protected abstract boolean isExist(Object key);

    protected abstract void doDelete(Object key);

    public void save(Resume r) {
        Object key = checkNotExist(r.getUuid());
        doSave(r, key);
    }

    public void update(Resume r) {
        Object key = checkExist(r.getUuid());
        doUpdate(r, key);
    }

    public void delete(String uuid) {
        Object key = checkExist(uuid);
        doDelete(key);
    }

    public Resume get(String uuid) {
        Object key = checkExist(uuid);
        return doGet(key);
    }

    private Object checkExist(String uuid) {
        Object key = getKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private Object checkNotExist(String uuid) {
        Object key = getKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }
    protected abstract List<Resume> getAll();

    public List<Resume> getAllSorted() {
        List<Resume> allResume = getAll();
        allResume.sort(Resume::compareTo);
        return allResume;
    }


}