package com.basejava.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlProcessor<ReturnType> {
    ReturnType query(PreparedStatement prepareStatement) throws SQLException;
}
