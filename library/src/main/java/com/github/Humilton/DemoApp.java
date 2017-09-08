package com.github.Humilton;

import android.app.Application;
import android.location.Location;

import com.github.Humilton.util.LocUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Hamilton on 2017/7/31.
 */

public class DemoApp extends Application {
    public static boolean isInChina = false;
    public static Gson gson;
    public static Location location;
    public static String formatted_address = "";

    @Override
    public void onCreate() {
        super.onCreate();
        isInChina = LocUtil.isChinaSimCard(this) != LocUtil.NOT_CHINA_SIM_CARD;
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
                return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
            }
        }).create();
    }
}
