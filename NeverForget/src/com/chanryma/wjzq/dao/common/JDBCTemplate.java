package com.chanryma.wjzq.dao.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chanryma.wjzq.exception.DBException;
import com.chanryma.wjzq.manager.DBManager;

public class JDBCTemplate<T> {
    private DBManager dbManager = (DBManager) com.chanryma.wjzq.factory.BeanFactory.getInstance().getBean("dbManager");

    public int getRecordCount(String sql, JDBCCallback<T> jdbcCallback) {
        Connection conn = dbManager.getConnection();
        PreparedStatement sttmt = null;
        ResultSet rs = null;
        try {
            sttmt = conn.prepareStatement(sql);
            if (jdbcCallback != null) {
                jdbcCallback.setParameters(sttmt);
            }
            rs = sttmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            dbManager.closeResource(rs, sttmt, conn);
        }
    }

    public List<T> queryList(String sql, JDBCCallback<T> jdbcCallback) {
        Connection conn = dbManager.getConnection();
        PreparedStatement sttmt = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();
        try {
            sttmt = conn.prepareStatement(sql);
            if (jdbcCallback != null) {
                jdbcCallback.setParameters(sttmt);
            }
            rs = sttmt.executeQuery();
            while (rs.next()) {
                T object = jdbcCallback.rsToObject(rs);
                list.add(object);
            }
            return list;
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            dbManager.closeResource(rs, sttmt, conn);
        }
    }

    public T queryOne(String sql, JDBCCallback<T> jdbcCallback) {
        List<T> list = queryList(sql, jdbcCallback);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public void insert(String sql, JDBCCallback<T> jdbcCallback) {
        Connection conn = dbManager.getConnection();
        PreparedStatement sttmt = null;
        try {
            sttmt = conn.prepareStatement(sql);
            if (jdbcCallback != null) {
                jdbcCallback.setParameters(sttmt);
            }
            sttmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            dbManager.closeResource(null, sttmt, conn);
        }
    }

    public void update(String sql, JDBCCallback<T> jdbcCallback) {
        Connection conn = dbManager.getConnection();
        PreparedStatement sttmt = null;
        try {
            sttmt = conn.prepareStatement(sql);
            if (jdbcCallback != null) {
                jdbcCallback.setParameters(sttmt);
            }
            sttmt.execute();
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            dbManager.closeResource(null, sttmt, conn);
        }
    }
}
