package com.chanryma.wjzq.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.chanryma.wjzq.domain.note.Note;
import com.chanryma.wjzq.domain.user.User;
import com.chanryma.wjzq.exception.InvalidRequestParameterException;
import com.chanryma.wjzq.exception.ServiceException;
import com.chanryma.wjzq.util.LogUtil;
import com.chanryma.wjzq.util.StringUtil;

public class NoteService extends BaseService {
    public int save(HttpServletRequest request) throws ServiceException, InvalidRequestParameterException {
        User user = findUser(request);

        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        String address = request.getParameter("address");
        String memo = request.getParameter("memo");
        String lockTime = request.getParameter("lockTime");

        if (StringUtil.isBlank(memo)) {
            throw new ServiceException("亲，你啥都不写，不太好吧……");
        }

        int lockTimeInt = ServiceUtil.parameterIntValue("lockTime", lockTime);

        if (lockTimeInt < 0) {
            LogUtil.error("Received negative lockTime '" + lockTime + "', adjust to default value 0.");
            lockTimeInt = 0;
        }

        Note note = new Note(user, memo);
        note.setAddress(address);
        note.setLongitude(longitude);
        note.setLatitude(latitude);
        note.setLockTime(lockTimeInt);
        int noteId = noteRepository.save(note);

        return noteId;
    }

    public List<Note> query(HttpServletRequest request) throws ServiceException {
        User user = findUser(request);
        List<Note> notes = noteRepository.queryWithUserId(user.getOpenId());

        return notes;
    }

    public Note queryOne(HttpServletRequest request) throws ServiceException, InvalidRequestParameterException {
        String noteId = request.getParameter("noteId");
        int noteIdInt = ServiceUtil.parameterIntValue("noteId", noteId);
        Note note = noteRepository.queryWithNoteId(noteIdInt);

        return note;
    }
}
