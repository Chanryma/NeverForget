package com.chanryma.wjzq.dao.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface JDBCCallback<T> {
    void setParameters(PreparedStatement sttmt) throws SQLException;

    T rsToObject(ResultSet rs) throws SQLException;
}
