package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            System.out.print("Хранилище полностью заполнено");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.print("Резюме с uuid " + resume.getUuid() + " уже есть");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.print("Резюме с uuid " + uuid + " не найдено");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
