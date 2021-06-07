package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public void save(Resume resume) {
        sqlHelper.transactionalExec(connection -> {
                    try (
                            PreparedStatement preparedStatement = connection.prepareStatement(
                                    "INSERT INTO resume (uuid,full_name) values (?,?)")) {
                        preparedStatement.setString(1, resume.getUuid());
                        preparedStatement.setString(2, resume.getFullName());
                        preparedStatement.execute();
                    }
                    insertContacts(connection, resume);
                    return null;
                }
        );
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExec(connection -> {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                String uuid = resume.getUuid();
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, uuid);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
                deleteContacts(connection, resume);
                insertContacts(connection, resume);
                return null;
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        return getResumes(uuid).get(uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        return new ArrayList<>(getResumes(null).values());
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

    private void insertContacts(Connection connection, Resume resume) throws SQLException {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, entry.getKey().name());
                preparedStatement.setString(3, entry.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void deleteContacts(Connection connection, Resume resume) throws SQLException {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "delete from contact where resume_uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private Map<String, Resume> getResumes(String uuid) {
        return sqlHelper.exec(
                "select * from resume" +
                        " left join contact on resume.uuid = contact.resume_uuid" +
                        (uuid != null ? " where uuid= ?" : ""),
                prepareStatement -> {
                    if (uuid != null) prepareStatement.setString(1, uuid);
                    ResultSet queryResult = prepareStatement.executeQuery();

                    Map<String, Resume> map = new LinkedHashMap<>();

                    if (!queryResult.next()) {
                        if (uuid != null) {
                            throw new NotExistStorageException(uuid);
                        }
                    } else {
                        do {
                            String currentUuid = queryResult.getString("uuid");
                            Resume resume = map.get(currentUuid);
                            if (resume == null) {
                                resume = new Resume(currentUuid, queryResult.getString("full_name"));
                                map.put(currentUuid, resume);
                            }
                            String type = queryResult.getString("type");
                            if (type != null) {
                                resume.addContact(ContactType.valueOf(type), queryResult.getString("value"));
                            }

                        } while (queryResult.next());
                    }
                    return map;
                });
    }
}