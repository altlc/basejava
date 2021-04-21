package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> list = new ArrayList<>();

    @Override
    protected Integer getKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    @Override
    protected Resume doGet(Integer key) {
        return list.get(key);
    }

    @Override
    protected void doSave(Resume r, Integer key) {
        list.add(r);
    }

    @Override
    protected void doUpdate(Resume r, Integer key) {
        list.set(key, r);
    }

    @Override
    protected void doDelete(Integer key) {
        list.remove((int)key);
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