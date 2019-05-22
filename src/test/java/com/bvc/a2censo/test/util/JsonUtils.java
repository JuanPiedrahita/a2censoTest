package com.bvc.a2censo.test.util;

import com.bvc.a2censo.test.model.AbstractJson;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonUtils {

    public static AbstractJson[] getJsonFromFile(String path, Class c){
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(path));
            return gson.fromJson(reader, c);
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

}
