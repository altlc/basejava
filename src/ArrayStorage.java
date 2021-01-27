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
        int index = getIndexFromUuid(resume.toString());
        if (index < storageSize) {
            storage[index] = resume;
        } else {
            storage[storageSize] = resume;
            storageSize++;
        }
    }

    Resume get(String uuid) {
        int index = getIndexFromUuid(uuid);
        if (index < storageSize) {
            return storage[index];
        }
        return new Resume();
    }

    void delete(String uuid) {
        //int i;
        for (int i = getIndexFromUuid(uuid); i < (storageSize - 1); i++) {
            storage[i] = storage[i + 1];
            storage[i + 1] = null;
        }
        storageSize--;
    }

    private int getIndexFromUuid(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return 10000;
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
