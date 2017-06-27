package com.chanryma.wjzq.service;

import com.chanryma.wjzq.exception.InvalidRequestParameterException;

public class ServiceUtil {
    public static int parameterIntValue(String name, String value) throws InvalidRequestParameterException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidRequestParameterException("参数" + name + "无效");
        }
    }
}
