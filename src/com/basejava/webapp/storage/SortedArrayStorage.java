package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            System.out.print("Хранилище полностью заполнено");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.print("Резюме с uuid " + resume.getUuid() + " уже есть");
        } else {
            index = -(index + 1);
            if (size > 0) {
                System.arraycopy(storage, index, storage, index + 1, size - index);
            }
            storage[index] = resume;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            if (size - 1 - index >= 0)
                System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            size--;
            storage[size] = null;
        } else {
            System.out.print("Резюме с uuid " + uuid + " не найдено");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}