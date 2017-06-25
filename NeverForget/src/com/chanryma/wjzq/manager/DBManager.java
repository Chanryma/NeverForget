package com.chanryma.wjzq.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.chanryma.wjzq.exception.DBException;
import com.chanryma.wjzq.model.ConfigEntity;
import com.chanryma.wjzq.util.ConfigUtil;

public class DBManager {
    private static String url;

    static {
        try {
            url = getUrl();
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void closeResource(ResultSet rs, Statement sttmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (sttmt != null) {
                sttmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getUrl() {
        StringBuffer buffer = new StringBuffer("jdbc:mysql://");
        buffer.append(ConfigUtil.get(ConfigEntity.KEY_HOST));
        buffer.append(":");
        buffer.append(ConfigUtil.get(ConfigEntity.KEY_PORT));
        buffer.append("/");
        buffer.append(ConfigUtil.get(ConfigEntity.KEY_DATABASE));
        buffer.append("?");
        buffer.append("user=");
        buffer.append(ConfigUtil.get(ConfigEntity.KEY_USER_NAME));
        buffer.append("&");
        buffer.append("password=");
        buffer.append(ConfigUtil.get(ConfigEntity.KEY_PASSWORD));
        String url = buffer.toString();

        return url;
    }
}
