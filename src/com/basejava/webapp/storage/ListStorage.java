package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    protected Object getKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    protected Resume doGet(Object key) {
        return list.get((int) key);
    }

    @Override
    protected void doSave(Resume r, Object key) {
        list.add(r);
    }

    @Override
    protected void doUpdate(Resume r, Object key) {
        list.set((int) key, r);
    }

    @Override
    protected void doDelete(Object key) {
        list.remove((int) key);
    }

    @Override
    public void clear() {
        list.clear();
    }

    public List<Resume> getAll() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }
}