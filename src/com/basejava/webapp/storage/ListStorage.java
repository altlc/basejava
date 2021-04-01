package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();
    public static final int STORAGE_LIMIT =0;

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(int index) {
        return index >= 0 && index < list.size();
    }

    @Override
    protected Resume doGet(int index) {
        return list.get(index);
    }

    @Override
    protected void doSave(Resume r, int index) {
        list.add(r);
    }

    @Override
    protected void doUpdate(Resume r, int index) {
        list.set(index, r);
    }

    @Override
    protected void doDelete(int index) {
        list.remove(index);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[list.size()]);
    }

    @Override
    public int size() {
        return list.size();
    }
}