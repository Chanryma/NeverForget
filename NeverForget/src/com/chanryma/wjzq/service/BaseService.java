package com.chanryma.wjzq.service;

import javax.servlet.http.HttpServletRequest;

import com.chanryma.wjzq.domain.note.NoteRepository;
import com.chanryma.wjzq.domain.user.User;
import com.chanryma.wjzq.domain.user.UserRepository;
import com.chanryma.wjzq.exception.ServiceException;
import com.chanryma.wjzq.factory.BeanFactory;
import com.chanryma.wjzq.util.LogUtil;
import com.chanryma.wjzq.util.StringUtil;

public class BaseService {
    protected NoteRepository noteRepository = (NoteRepository) BeanFactory.getInstance().getBean("noteRepository");
    protected UserRepository userRepository = (UserRepository) BeanFactory.getInstance().getBean("userRepository");

    protected User findUser(HttpServletRequest request) throws ServiceException {
        String sessionCode = request.getParameter("sessionCode");
        if (StringUtil.isEmpty(sessionCode)) {
            LogUtil.debug("sessionCode=" + sessionCode);
            throw new ServiceException("敢问阁下从哪来？好像不太认识你呀。");
        }

        User user = userRepository.findWithSessionCode(sessionCode);
        if (user == null) {
            throw new ServiceException("亲，我这儿没查到你这个人啊……");
        }

        LogUtil.info("Welcome " + user.getNickName() + ".");

        return user;
    }
}
