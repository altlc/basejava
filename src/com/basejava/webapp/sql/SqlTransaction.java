package com.basejava.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlTransaction<T> {
    T exec(Connection connection) throws SQLException;
}