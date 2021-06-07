package com.basejava.webapp.sql;

import com.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T exec(String sql, SqlQuery<T> query) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            return query.exec(ps);
        } catch (SQLException e) {
            throw ExceptionConvertor.convertException(e);
        }
    }

    public <T> T transactionalExec(SqlTransaction<T> transaction) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T result = transaction.exec(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw ExceptionConvertor.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
