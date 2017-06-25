package com.chanryma.wjzq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chanryma.wjzq.exception.ServiceException;
import com.chanryma.wjzq.model.ResponseEntity;
import com.chanryma.wjzq.util.Constant;
import com.chanryma.wjzq.util.GsonUtil;
import com.chanryma.wjzq.util.LogUtil;
import com.mysql.jdbc.StringUtils;

public abstract class BaseServlet extends HttpServlet {
    private static final long serialVersionUID = 6526365171833095344L;
    private Object service;
    private static Map<String, String> getPath2MethodNames;
    private static Map<String, String> postPath2MethodNames;

    public abstract Object getService();

    public abstract void initGetPath2MethodNames(Map<String, String> getPath2MethodNames);

    public abstract void initPostPath2MethodNames(Map<String, String> postPath2MethodNames);

    public BaseServlet() {
        super();
        service = getService();

        if ((getPath2MethodNames == null) || (postPath2MethodNames == null)) {
            getPath2MethodNames = new HashMap<String, String>();
            postPath2MethodNames = new HashMap<String, String>();
            initGetPath2MethodNames(getPath2MethodNames);
            initPostPath2MethodNames(postPath2MethodNames);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            handleRequest(request, response, false);
        } catch (ServiceException e) {
            ResponseEntity responseEntity = new ResponseEntity();
            responseEntity.setStatus(Constant.RESULT_CODE_ERROR);
            responseEntity.setMsg(e.getMessage());
            sendResponse(response, responseEntity);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            handleRequest(request, response, true);
        } catch (ServiceException e) {
            ResponseEntity responseEntity = new ResponseEntity();
            responseEntity.setStatus(Constant.RESULT_CODE_ERROR);
            responseEntity.setMsg(e.getMessage());
            sendResponse(response, responseEntity);
        }
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response, boolean isPost) throws ServiceException, UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String methodName = getMethodName(request, isPost);

        LogUtil.info("methodName=" + methodName + ", isPost=" + isPost);
        logParameters(request);

        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmptyOrWhitespaceOnly(methodName)) {
            sendResponse(response, responseEntity);
            return;
        }

        try {
            Method method = service.getClass().getMethod(methodName, HttpServletRequest.class);
            responseEntity = (ResponseEntity) method.invoke(service, request);
        } catch (NoSuchMethodException e) {
            LogUtil.error(e);
        } catch (SecurityException e) {
            LogUtil.error(e);
        } catch (IllegalAccessException e) {
            LogUtil.error(e);
        } catch (IllegalArgumentException e) {
            LogUtil.error(e);
        } catch (InvocationTargetException e) {
            LogUtil.error(e);
            throw new ServiceException(e.getCause().getMessage());
        }

        sendResponse(response, responseEntity);
    }

    private String getMethodName(HttpServletRequest request, boolean isPost) {
        String requestUrl = request.getRequestURL().toString();
        Map<String, String> path2MethodNames = isPost ? postPath2MethodNames : getPath2MethodNames;
        Iterator<String> iterator = path2MethodNames.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (requestUrl.endsWith(key)) {
                return path2MethodNames.get(key);
            }
        }

        return null;
    }

    private void sendResponse(HttpServletResponse response, ResponseEntity responseEntity) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(GsonUtil.objectToJson(responseEntity));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private void logParameters(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        Map<String, String> map = new HashMap<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            map.put(name, value);
        }

        LogUtil.debug("request parameters: " + map.toString());
    }
}
