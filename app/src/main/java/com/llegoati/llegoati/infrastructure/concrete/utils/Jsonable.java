package com.llegoati.llegoati.infrastructure.concrete.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.llegoati.llegoati.infrastructure.abstracts.IJsonable;

/**
 * Created by Yansel on 2/16/2017.
 */

public class Jsonable implements IJsonable {

    protected static Gson gson;

    @Override
    public String toJson() {
        if (gson == null)
            gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public JsonElement toJsonElement() {
        if (gson == null)
            gson = new Gson();
        return gson.toJsonTree(this);
    }

    public static Object fromJson(String json, Class class_object) {

        if (gson == null)
            gson = new Gson();
        return gson.fromJson(json, class_object);
    }

    public static String toJson(Object obj) {
        if (gson == null)
            gson = new Gson();
        return gson.toJson(obj);
    }
}
