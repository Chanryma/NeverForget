package com.chanryma.wjzq.domain.attachment;

import java.util.List;

import com.chanryma.wjzq.dao.AttachmentDao;
import com.chanryma.wjzq.factory.BeanFactory;

public class AttachmentRepository {
    private AttachmentDao attachmentDao = (AttachmentDao) BeanFactory.getInstance().getBean("attachmentDao");

    public void save(Attachment attachment) {
        attachmentDao.save(attachment);
    }

    public List<Attachment> queryWithNoteId(int noteId) {
        return attachmentDao.queryWithNoteId(noteId);
    }
}
