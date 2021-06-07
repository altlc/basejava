package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.List;

public interface Storage {

    void clear();

    void delete(String uuid);

    void save(Resume r);

    void update(Resume r);

    Resume get(String uuid);

    List<Resume> getAllSorted();

    int size();
}