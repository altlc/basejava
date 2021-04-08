package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageByResume extends AbstractStorage {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected Object getKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected Resume doGet(Object key) {
        return (Resume) key;
    }

    @Override
    protected void doUpdate(Resume r, Object key) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object key) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }

    @Override
    protected void doDelete(Object key) {
        map.remove(((Resume) key).getUuid());
    }

    @Override
    public void clear() {
        map.clear();
    }

    public List<Resume> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
