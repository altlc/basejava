package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    public abstract int getLimit();

    protected abstract int getIndex(String uuid);

    protected abstract Resume realGet(int index);

    protected abstract void realUpdate(Resume r, int index);

    protected abstract void realSave(Resume r);

    protected abstract boolean realCheckExists(int index);

    protected abstract void realDelete(int index);

    public void save(Resume r) {
        checkNotExists(r.getUuid());
        realSave(r);
    }

    public void update(Resume r) {
        int index = checkExists(r.getUuid());
        realUpdate(r, index);
    }

    public void delete(String uuid) {
        int index = checkExists(uuid);
        realDelete(index);
    }

    public Resume get(String uuid) {
        int index = checkExists(uuid);
        return realGet(index);
    }

    private int checkExists(String uuid) {
        int index = getIndex(uuid);
        if (!realCheckExists(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    private int checkNotExists(String uuid) {
        int index = getIndex(uuid);
        if (realCheckExists(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }
}