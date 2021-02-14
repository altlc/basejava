package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10_000];
    private int storageSize;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void save(Resume resume) {
        if (storageSize >= storage.length) {
            System.out.print("Хранилище полностью заполнено");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.print("Резюме с uuid " + resume.getUuid() + " уже есть");
        } else {
            storage[storageSize] = resume;
            storageSize++;
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.print("Резюме с uuid " + resume.getUuid() + " не найдено");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.print("Резюме с uuid " + uuid + " не найдено");
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            if (storageSize - 1 - index >= 0)
                System.arraycopy(storage, index + 1, storage, index, storageSize - 1 - index);
            storageSize--;
            storage[storageSize] = null;
        } else {
            System.out.print("Резюме с uuid " + uuid + " не найдено");
        }
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
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
