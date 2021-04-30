package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serializer.Serializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final Serializer serializer;

    protected PathStorage(String dir, Serializer serializer) {
        directory = Paths.get(dir);
        this.serializer = serializer;
        Objects.requireNonNull(directory, "Название папки не может быть пустым");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " не папка");
        }
        if (!Files.isWritable(directory) || !Files.isReadable(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " не позволяет производить чтение/запись");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Ошибка удаления файла", null);
        }
    }

    @Override
    public int size() {
        List<String> files = new ArrayList<>();
        try {
            Files.list(directory).forEach(file -> files.add(file.toString()));
        } catch (IOException ex) {
            throw new StorageException("Ошибка чтения папки", null);
        }
        return files.size();
    }

    @Override
    protected Path getKey(String uuid) {
        return Path.of(directory.toString(),uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path file) {
        try {
            serializer.doPutFile(r, new BufferedOutputStream(Files.newOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Ошибка записи файла ", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    protected void doSave(Resume r, Path file) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Не возможно создать файл " + file.toAbsolutePath(), getFileName(file), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return serializer.doGetFile(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Ошибка чтения файла ", getFileName(file), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Ошибка удаления файла ", getFileName(file));
        }

    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> list = new ArrayList<>();
        try {
            Files.list(directory).forEach(file -> list.add(doGet(file)));
        } catch (IOException ex) {
            throw new StorageException("Ошибка чтения папки", null);
        }
        return list;
    }

    protected String getFileName(Path file) {
        return file.getFileName().toString();
    }

}