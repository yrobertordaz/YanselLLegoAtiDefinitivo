package com.llegoati.llegoati.infrastructure.abstracts;

import com.google.gson.JsonElement;

/**
 * Created by Yansel on 2/16/2017.
 */
public interface IJsonable {
    String toJson();

    JsonElement toJsonElement();
}
