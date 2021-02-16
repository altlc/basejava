package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.print("Резюме с uuid " + uuid + " не найдено");
        return null;
    }

    public void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            System.out.print("Хранилище полностью заполнено");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.print("Резюме с uuid " + resume.getUuid() + " уже есть");
        } else {
            addElement(resume, index);
            size++;
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

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteElement(index);
            size--;
            storage[size] = null;
        } else {
            System.out.print("Резюме с uuid " + uuid + " не найдено");
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void addElement(Resume resume, int index);

    protected abstract void deleteElement(int index);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

}
