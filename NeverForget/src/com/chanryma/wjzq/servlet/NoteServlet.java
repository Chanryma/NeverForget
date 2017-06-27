package com.chanryma.wjzq.servlet;

import java.util.Map;

import com.chanryma.wjzq.factory.BeanFactory;

public class NoteServlet extends BaseServlet {
    private static final long serialVersionUID = -4324849279895452637L;

    @Override
    public Object getService() {
        return BeanFactory.getInstance().getBean("noteService");
    }

    @Override
    public void initGetPath2MethodNames(Map<String, String> getPath2MethodNames) {
        getPath2MethodNames.put("/note/query", "query");
        getPath2MethodNames.put("/note/queryOne", "queryOne");
    }

    @Override
    public void initPostPath2MethodNames(Map<String, String> postPath2MethodNames) {
        postPath2MethodNames.put("/note/add", "save");
    }
}
