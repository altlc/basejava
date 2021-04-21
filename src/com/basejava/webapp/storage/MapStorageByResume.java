package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageByResume extends AbstractStorage<Resume> {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected Resume getKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected Resume doGet(Resume key) {
        return key;
    }

    @Override
    protected void doUpdate(Resume r, Resume key) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Resume key) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Resume key) {
        return key != null;
    }

    @Override
    protected void doDelete(Resume key) {
        map.remove(key.getUuid());
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
