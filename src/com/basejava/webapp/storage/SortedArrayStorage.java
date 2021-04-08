package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> COMPARATOR = (Resume1, Resume2) -> Resume1.getUuid().compareTo(Resume2.getUuid());

    protected void addElement(Resume resume, int index) {
        index = -(index + 1);
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    protected void deleteElement(int index) {
        if (size - 1 - index >= 0)
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }

    @Override
    protected Object getKey(String uuid) {
        Resume searchKey = new Resume(uuid,"DUMMY");
        //для поиска ключа не нужно сравнивать fullName, используем свой COMPARATOR
        return Arrays.binarySearch(storage, 0, size, searchKey,COMPARATOR);
    }
}