package com.chanryma.wjzq.dao.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbsJDBCCallback<T> implements JDBCCallback<T> {

    @Override
    public void setParameters(PreparedStatement sttmt) throws SQLException {

    }

    @Override
    public T rsToObject(ResultSet rs) throws SQLException {
        return null;
    }
}
