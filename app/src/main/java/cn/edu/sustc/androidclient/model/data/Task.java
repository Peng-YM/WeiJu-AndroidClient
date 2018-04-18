package cn.edu.sustc.androidclient.model.data;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class Task implements Serializable {
    public String id;
    public String xml;
    public String title;
    public String start;
    public String end;
    public String descriptions;
    public String author;
    public List<String> pictures;

    @Override
    public String toString() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);
        return jsonString;
    }
}
