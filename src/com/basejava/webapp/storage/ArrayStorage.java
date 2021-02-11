package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int storageSize;

    public void clear() {
        Arrays.fill(storage, 0, storageSize - 1, null);
        storageSize = 0;
    }

    public void save(Resume resume) {
        if (storageSize >= 10000) {
            System.out.print("Хранилище полностью заполнено");
            return;
        }
        int index = getIndex(resume.toString());
        if (index >= 0) {
            System.out.print("Резюме с таким uuid уже есть");
        } else {
            storage[storageSize] = resume;
            storageSize++;
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.toString());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.print("Резюме не найдено");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.print("Резюме не найдено");
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            for (int i = index; i < (storageSize - 1); i++) {
                storage[i] = storage[i + 1];
            }
            storageSize--;
            storage[storageSize] = null;
        } else {
            System.out.print("Резюме не найдено");
        }
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    public int size() {
        return storageSize;
    }
}
