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
    public Resume get(String uuid) {
        return sqlHelper.exec(
                "SELECT * FROM resume r" +
                        " left join contact c ON r.uuid = c.resume_uuid" +
                        " WHERE r.uuid =?",
                prepareStatement -> {
                    prepareStatement.setString(1, uuid);
                    ResultSet queryResult = prepareStatement.executeQuery();
                    if (!queryResult.next()) {
                        throw new NotExistStorageException(uuid);
                    }

                    Resume resume = new Resume(uuid, queryResult.getString("full_name"));

                    do {
                        String type = queryResult.getString("type");
                        String value = queryResult.getString("value");
                        if (type != null && value != null) {
                            resume.addContact(ContactType.valueOf(type), value);
                        }
                    } while (queryResult.next());

                    return resume;
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
                "SELECT uuid FROM resume r ORDER BY full_name,uuid",
                prepareStatement -> {
                    ResultSet queryResult = prepareStatement.executeQuery();
                    List<Resume> resumes = new ArrayList<>();
                    while (queryResult.next()) {
                        resumes.add(
                                get(queryResult.getString("uuid"))
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

}