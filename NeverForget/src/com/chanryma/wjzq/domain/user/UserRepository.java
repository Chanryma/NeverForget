package com.chanryma.wjzq.domain.user;

import com.chanryma.wjzq.dao.UserDao;
import com.chanryma.wjzq.factory.BeanFactory;

public class UserRepository {
    private UserDao userDao = (UserDao) BeanFactory.getInstance().getBean("userDao");

    public void saveUser(User user) {
        userDao.save(user);
    }

    public User findWithSessionCode(String sessionCode) {
        return userDao.findWithSessionCode(sessionCode);
    }
}
