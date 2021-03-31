package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public interface Storage {

    int getLimit();

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();
}