package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.exec(
                "DELETE FROM resume",
                PreparedStatement::execute
        );
    }

    @Override
    public void update(Resume r) {
        sqlHelper.exec(
                "UPDATE resume SET full_name = ? WHERE uuid = ?",
                prepareStatement -> {
                    String uuid = r.getUuid();
                    prepareStatement.setString(1, r.getFullName());
                    prepareStatement.setString(2, uuid);
                    if (prepareStatement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.exec(
                "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                prepareStatement -> {
                    prepareStatement.setString(1, r.getUuid());
                    prepareStatement.setString(2, r.getFullName());
                    prepareStatement.execute();
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.exec(
                "SELECT * FROM resume r WHERE r.uuid =?",
                prepareStatement -> {
                    prepareStatement.setString(1, uuid);
                    ResultSet queryResult = prepareStatement.executeQuery();
                    if (!queryResult.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, queryResult.getString("full_name"));
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.exec(
                "DELETE FROM resume WHERE uuid=?",
                prepareStatement -> {
                    prepareStatement.setString(1, uuid);
                    if (prepareStatement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.exec(
                "SELECT * FROM resume r ORDER BY full_name,uuid",
                prepareStatement -> {
                    ResultSet queryResult = prepareStatement.executeQuery();
                    List<Resume> resumes = new ArrayList<>();
                    while (queryResult.next()) {
                        resumes.add(
                                new Resume(
                                        queryResult.getString("uuid"),
                                        queryResult.getString("full_name")
                                )
                        );
                    }
                    return resumes;
                }
        );
    }

    @Override
    public int size() {
        return sqlHelper.exec(
                "SELECT count(*) FROM resume",
                prepareStatement -> {
                    ResultSet queryResult = prepareStatement.executeQuery();
                    return queryResult.next() ? queryResult.getInt(1) : 0;
                }
        );
    }
}