package com.chanryma.wjzq.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chanryma.wjzq.dao.common.AbsJDBCCallback;
import com.chanryma.wjzq.dao.common.JDBCCallback;
import com.chanryma.wjzq.dao.common.JDBCTemplate;
import com.chanryma.wjzq.domain.user.User;

public class UserDao {
    private JDBCTemplate<User> jdbcTemplate = new JDBCTemplate<>();

    public void save(final User user) {
        String sql = "INSERT INTO user (`openId`,`sessionCode`, `nickName`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `sessionCode` = ?, `nickName` = ?;";
        jdbcTemplate.insert(sql, new AbsJDBCCallback<User>() {
            @Override
            public void setParameters(PreparedStatement sttmt) throws SQLException {
                sttmt.setString(1, user.getOpenId());
                sttmt.setString(2, user.getSessionCode());
                sttmt.setString(3, user.getNickName());
                sttmt.setString(4, user.getSessionCode());
                sttmt.setString(5, user.getNickName());
            }
        });
    }

    public User findWithSessionCode(final String sessionCode) {
        String sql = "SELECT `gid`, `openId`, `sessionCode`, `nickName` FROM `user` WHERE `sessionCode` = ?;";

        return jdbcTemplate.queryOne(sql, new JDBCCallback<User>() {

            @Override
            public void setParameters(PreparedStatement sttmt) throws SQLException {
                sttmt.setString(1, sessionCode);
            }

            @Override
            public User rsToObject(ResultSet rs) throws SQLException {
                int id = rs.getInt("gid");
                String openId = rs.getString("openId");
                String sessionCode = rs.getString("sessionCode");
                String nickName = rs.getString("nickName");

                User user = new User(openId, nickName);
                user.setSessionCode(sessionCode);
                user.setId(id);

                return user;
            }
        });
    }
}
