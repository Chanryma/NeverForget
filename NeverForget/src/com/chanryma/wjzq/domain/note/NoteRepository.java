package com.chanryma.wjzq.domain.note;

import java.util.List;

import com.chanryma.wjzq.dao.NoteDao;
import com.chanryma.wjzq.factory.BeanFactory;

public class NoteRepository {
    private NoteDao noteDao = (NoteDao) BeanFactory.getInstance().getBean("noteDao");

    public int save(final Note note) {
        return noteDao.save(note);
    }

    public List<Note> queryWithUserId(final String userId) {
        return noteDao.queryWithUserId(userId);
    }
}
