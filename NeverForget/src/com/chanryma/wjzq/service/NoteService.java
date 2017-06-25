package com.chanryma.wjzq.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.chanryma.wjzq.domain.note.Note;
import com.chanryma.wjzq.domain.user.User;
import com.chanryma.wjzq.exception.ServiceException;
import com.chanryma.wjzq.model.ResponseEntity;
import com.chanryma.wjzq.util.Constant;
import com.chanryma.wjzq.util.LogUtil;
import com.chanryma.wjzq.util.StringUtil;

public class NoteService extends BaseService {
    public ResponseEntity save(HttpServletRequest request) throws ServiceException {
        User user = findUser(request);

        ResponseEntity responseEntity = new ResponseEntity();
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        String address = request.getParameter("address");
        String memo = request.getParameter("memo");
        String lockTime = request.getParameter("lockTime");

        if (StringUtil.isBlank(memo)) {
            throw new ServiceException("亲，你啥都不写，不太好吧……");
        }

        int lockTimeInt = 0;
        try {
            lockTimeInt = Integer.parseInt(lockTime);
        } catch (NumberFormatException e) {
            LogUtil.error("Received invalid lockTime '" + lockTime + "'.");
        }

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
        responseEntity.setStatus(Constant.RESULT_CODE_SUCCESS);
        responseEntity.setData(noteId);

        return responseEntity;
    }

    public ResponseEntity query(HttpServletRequest request) throws ServiceException {
        User user = findUser(request);

        ResponseEntity responseEntity = new ResponseEntity();
        List<Note> notes = noteRepository.queryWithUserId(user.getOpenId());
        responseEntity.setStatus(Constant.RESULT_CODE_SUCCESS);
        responseEntity.setData(notes);

        return responseEntity;
    }
}
