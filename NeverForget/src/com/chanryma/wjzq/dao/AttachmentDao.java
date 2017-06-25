package com.chanryma.wjzq.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.chanryma.wjzq.dao.common.AbsJDBCCallback;
import com.chanryma.wjzq.dao.common.JDBCCallback;
import com.chanryma.wjzq.dao.common.JDBCTemplate;
import com.chanryma.wjzq.domain.attachment.Attachment;
import com.chanryma.wjzq.domain.user.User;

public class AttachmentDao {
    private JDBCTemplate<Attachment> jdbcTemplate = new JDBCTemplate<>();

    public void save(final Attachment attachment) {
        String sql = "INSERT INTO attachment (`noteId`,`createdOn`, `createdBy`, `path`) VALUES (?,?,?,?);";
        jdbcTemplate.insert(sql, new AbsJDBCCallback<Attachment>() {
            @Override
            public void setParameters(PreparedStatement sttmt) throws SQLException {
                sttmt.setInt(1, attachment.getNoteId());
                Timestamp createdOn = new Timestamp(System.currentTimeMillis());
                sttmt.setTimestamp(2, createdOn);
                sttmt.setString(3, attachment.getCreatedBy().getOpenId());
                sttmt.setString(4, attachment.getPath());
            }
        });
    }

    public List<Attachment> queryWithNoteId(final int noteId) {
        String sql = "select `id`, `noteId`, `createdOn`, `path`，`createdBy` from attachment where noteId = ?;";

        return jdbcTemplate.queryList(sql, new JDBCCallback<Attachment>() {

            @Override
            public void setParameters(PreparedStatement sttmt) throws SQLException {
                sttmt.setInt(1, noteId);
            }

            @Override
            public Attachment rsToObject(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                int noteId = rs.getInt("noteId");
                Timestamp createdOn = rs.getTimestamp("createdOn");
                String path = rs.getString("path");
                String createdById = rs.getString("createdBy");

                User createdBy = new User(createdById, "");
                Attachment attachment = new Attachment(path);
                attachment.setId(id);
                attachment.setNoteId(noteId);
                attachment.setCreatedBy(createdBy);
                attachment.setCreatedOn(createdOn.getTime());

                return attachment;
            }
        });
    }
}
