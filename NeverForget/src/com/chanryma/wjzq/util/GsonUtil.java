package com.chanryma.wjzq.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonUtil {
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    public static String objectToJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T jsonToObject(String json, Class<T> cls) {
        return gson.fromJson(json, cls);
    }

    public static JsonObject json2JsonObject(String json) {
        return new JsonParser().parse(json).getAsJsonObject();
    }
}
