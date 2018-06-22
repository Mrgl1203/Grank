package com.gulei.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by gl152 on 2018/6/15.
 */

public class GsonUtil {
    //将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder().registerTypeAdapter(String.class, STRING).create();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    //将对象转换成Json数据
    public static String toJsonString(Object obj) {
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder().registerTypeAdapter(String.class, STRING).create();
        return gson.toJson(obj);
    }

    //返回字段为null时 Gson会排除掉该字段，所以定义adapter将null替换为""
    private static final TypeAdapter STRING = new TypeAdapter() {
        @Override
        public void write(JsonWriter writer, Object value) throws IOException {
            if (value == null) {
                // 在这里处理null改为空字符串
                writer.value("");
                return;
            }
            writer.value((String) value);
        }


        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }


    };
}
