package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.*;
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

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) throws ClassNotFoundException {
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
                    insertSections(connection, resume);
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
                deleteSections(connection, resume);

                insertContacts(connection, resume);
                insertSections(connection, resume);
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

    private void insertSections(Connection connection, Resume resume) throws SQLException {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO section (resume_uuid, type, content) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, entry.getKey().name());

                switch (entry.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        preparedStatement.setString(3, ((TextSection)entry.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        preparedStatement.setString(3, String.join("\n", ((ListSection) entry.getValue()).getContentList()));
                        break;
                }
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

    private void deleteSections(Connection connection, Resume resume) throws SQLException {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "delete from section where resume_uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private Map<String, Resume> getResumes(String uuid) {
        return sqlHelper.transactionalExec(connection -> {
            Map<String, Resume> map = new LinkedHashMap<>();

            try (PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM resume " +
                    (uuid != null ? " where uuid= ?" : "") +
                    " ORDER BY full_name, uuid")) {
                if (uuid != null) prepareStatement.setString(1, uuid);
                ResultSet queryResult = prepareStatement.executeQuery();

                if (!queryResult.next()) {
                    if (uuid != null) {
                        throw new NotExistStorageException(uuid);
                    }
                } else {
                    do {
                        String resume_uuid = queryResult.getString("uuid");
                        map.put(resume_uuid, new Resume(resume_uuid, queryResult.getString("full_name")));
                    } while (queryResult.next());
                }
            }

            try (PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM contact " +
                    (uuid != null ? " where resume_uuid= ?" : ""))) {
                if (uuid != null) prepareStatement.setString(1, uuid);
                ResultSet queryResult = prepareStatement.executeQuery();
                while (queryResult.next()) {
                    String resume_uuid = queryResult.getString("resume_uuid");
                    Resume resume = map.get(resume_uuid);
                    String value = queryResult.getString("value");
                    if (value != null) {
                        resume.addContact(ContactType.valueOf(queryResult.getString("type")), value);
                    }
                }
            }

            try (PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM section " +
                    (uuid != null ? " where resume_uuid= ?" : ""))) {
                if (uuid != null) prepareStatement.setString(1, uuid);
                ResultSet queryResult = prepareStatement.executeQuery();
                while (queryResult.next()) {
                    String resume_uuid = queryResult.getString("resume_uuid");
                    Resume resume = map.get(resume_uuid);
                    String content = queryResult.getString("content");
                    SectionType type = SectionType.valueOf(queryResult.getString("type"));
                    Section section = null;
                    if (content != null) {
                        switch (type) {
                            case OBJECTIVE:
                            case PERSONAL:
                                section = new TextSection(content);
                                break;
                            case ACHIEVEMENT:
                            case QUALIFICATIONS:
                                section = new ListSection(content.split("\\R"));
                                break;
                        }
                        resume.addSection(type, section);
                    }
                }
            }

            return map;
        });
    }
}