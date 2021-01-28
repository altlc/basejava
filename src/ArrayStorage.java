import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int storageSize;

    void clear() {
        for (int i = 0; i < storageSize; i++) {
            storage[i] = null;
        }
        storageSize = 0;
    }

    void save(Resume resume) {
        int index = getIndex(resume.toString());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            storage[storageSize] = resume;
            storageSize++;
        }
    }

    Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        return null;
    }

    void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            for (int i = index; i < (storageSize - 1); i++) {
                storage[i] = storage[i + 1];
            }
            storageSize--;
            storage[storageSize] = null;
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
    Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    int size() {
        return storageSize;
    }
}
