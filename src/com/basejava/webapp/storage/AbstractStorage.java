package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract int getIndex(String uuid);

    protected abstract Resume doGet(int index);

    protected abstract void doUpdate(Resume r, int index);

    protected abstract void doSave(Resume r, int index);

    protected abstract boolean isExist(int index);

    protected abstract void doDelete(int index);

    public void save(Resume r) {
        int index = checkNotExist(r.getUuid());
        doSave(r, index);
    }

    public void update(Resume r) {
        int index = checkExist(r.getUuid());
        doUpdate(r, index);
    }

    public void delete(String uuid) {
        int index = checkExist(uuid);
        doDelete(index);
    }

    public Resume get(String uuid) {
        int index = checkExist(uuid);
        return doGet(index);
    }

    private int checkExist(String uuid) {
        int index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    private int checkNotExist(String uuid) {
        int index = getIndex(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }
}