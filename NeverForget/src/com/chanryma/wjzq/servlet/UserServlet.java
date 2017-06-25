package com.chanryma.wjzq.servlet;

import java.util.Map;

import com.chanryma.wjzq.factory.BeanFactory;

public class UserServlet extends BaseServlet {
    private static final long serialVersionUID = -4641900665201197604L;

    @Override
    public Object getService() {
        return BeanFactory.getInstance().getBean("userService");
    }

    @Override
    public void initGetPath2MethodNames(Map<String, String> getPath2MethodNames) {

    }

    @Override
    public void initPostPath2MethodNames(Map<String, String> postPath2MethodNames) {
        postPath2MethodNames.put("/login", "login");
    }
}
