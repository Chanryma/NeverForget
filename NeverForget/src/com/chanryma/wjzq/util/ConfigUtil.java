package com.chanryma.wjzq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class ConfigUtil {
    private static final String INI_FILE_NAME = "Config.ini";
    private static HashMap<String, String> propertyMap = null;

    static {
        propertyMap = new HashMap<String, String>();

        boolean isWindows = true;
        String url;
        if (isWindows) {
            url = new ConfigUtil().getClass().getClassLoader().getResource(INI_FILE_NAME).toString().substring(6);
            url = url.replace("%20", " ");
        } else {
            url = new ConfigUtil().getClass().getClassLoader().getResource(INI_FILE_NAME).toString().substring(5);
        }

        LogUtil.info("url=" + url);

        File file = new File(url);

        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Properties properties = new Properties();
        try {
            properties.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        @SuppressWarnings("unchecked")
        Enumeration<String> keys = (Enumeration<String>) properties.propertyNames();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            propertyMap.put(key.toLowerCase(), properties.getProperty(key));
        }
    }

    public static String get(String key) {
        return propertyMap.get(key.toLowerCase());
    }

    public static int getInt(String key) {
        String valueStr = propertyMap.get(key.toLowerCase());
        int value = 0;
        try {
            value = Integer.valueOf(valueStr);
        } catch (Exception e) {
            e.printStackTrace();
            value = 0;
        }
        return value;
    }

    public static void set(String key, String value) {
        if (get(key) == null) {
            propertyMap.put(key, value);
        }
    }
}
