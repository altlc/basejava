package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<StorageKey> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public void update(Resume r) {
        LOG.info("Update " + r);
        StorageKey key = checkExist(r.getUuid());
        doUpdate(r, key);
    }

    public void save(Resume r) {
        LOG.info("Save " + r);
        StorageKey key = checkNotExist(r.getUuid());
        doSave(r, key);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        StorageKey key = checkExist(uuid);
        return doGet(key);
    }

    protected abstract Resume doGet(StorageKey key);

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        StorageKey key = checkExist(uuid);
        doDelete(key);
    }

    protected abstract void doDelete(StorageKey key);

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> allResume = getAll();
        allResume.sort(Resume::compareTo);
        return allResume;
    }

    protected abstract List<Resume> getAll();

    private StorageKey checkNotExist(String uuid) {
        StorageKey key = getKey(uuid);
        if (isExist(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract void doSave(Resume r, StorageKey key);

    protected abstract StorageKey getKey(String uuid);

    protected abstract boolean isExist(StorageKey key);

    private StorageKey checkExist(String uuid) {
        StorageKey key = getKey(uuid);
        if (!isExist(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract void doUpdate(Resume r, StorageKey key);

}