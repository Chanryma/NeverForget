package com.chanryma.wjzq.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.chanryma.wjzq.dao.common.AbsJDBCCallback;
import com.chanryma.wjzq.dao.common.JDBCCallback;
import com.chanryma.wjzq.dao.common.JDBCTemplate;
import com.chanryma.wjzq.domain.note.Note;
import com.chanryma.wjzq.domain.user.User;

public class NoteDao {
    private JDBCTemplate<Note> jdbcTemplate = new JDBCTemplate<>();

    public int save(final Note note) {
        String sql = "INSERT INTO note (`parentId`,`createdOn`, `createdBy`, `memo`, `lockTime`,`address`, `longitude`, `latitude`) VALUES (?,?,?,?,?,?,?,?);";
        jdbcTemplate.insert(sql, new AbsJDBCCallback<Note>() {
            @Override
            public void setParameters(PreparedStatement sttmt) throws SQLException {
                Timestamp createdOn = new Timestamp(System.currentTimeMillis());
                sttmt.setInt(1, note.getParentId());
                sttmt.setTimestamp(2, createdOn);
                sttmt.setString(3, note.getCreatedBy().getOpenId());
                sttmt.setString(4, note.getMemo());
                sttmt.setInt(5, note.getLockTime());
                sttmt.setString(6, note.getAddress());
                sttmt.setString(7, note.getLongitude());
                sttmt.setString(8, note.getLatitude());
            }
        });

        return getLatestId();
    }

    public List<Note> queryWithUserId(final String userId) {
        String sql = "SELECT `id`, `parentId`, `createdOn`, `createdBy`, `memo`, `lockTime`, `address`, `longitude`, `latitude` FROM `note` where createdBy = ?;";

        return jdbcTemplate.queryList(sql, new JDBCCallback<Note>() {

            @Override
            public void setParameters(PreparedStatement sttmt) throws SQLException {
                sttmt.setString(1, userId);
            }

            @Override
            public Note rsToObject(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                int parentId = rs.getInt("parentId");
                Timestamp createdOn = rs.getTimestamp("createdOn");
                String createdById = rs.getString("createdBy");
                String memo = rs.getString("memo");
                int lockTime = rs.getInt("lockTime");
                String address = rs.getString("address");
                String longitude = rs.getString("longitude");
                String latitude = rs.getString("latitude");

                User createdBy = new User(createdById, "");

                Note note = new Note(createdBy, memo);
                note.setId(id);
                note.setParentId(parentId);
                note.setCreatedOn(createdOn.getTime());
                note.setLockTime(lockTime);
                note.setAddress(address);
                note.setLongitude(longitude);
                note.setLatitude(latitude);

                return note;
            }
        });
    }

    private int getLatestId() {
        String sql = "SELECT `id` FROM `note` ORDER BY id DESC LIMIT 0, 1;";

        Note note = jdbcTemplate.queryOne(sql, new JDBCCallback<Note>() {

            @Override
            public void setParameters(PreparedStatement sttmt) throws SQLException {

            }

            @Override
            public Note rsToObject(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");

                Note note = new Note(null, null);
                note.setId(id);

                return note;
            }
        });

        return (note == null) ? -1 : note.getId();
    }
}
