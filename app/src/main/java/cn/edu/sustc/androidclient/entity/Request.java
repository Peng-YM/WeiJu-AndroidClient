package cn.edu.sustc.androidclient.entity;


import java.util.HashMap;

public class Request {
    public enum requestMethod {
        GET, DELETE, PUT, PATCH
    }
    private requestMethod method;
    private String URL;
    private HashMap<String, String> headers;
    private String body;

    public Request(requestMethod method, String URL, HashMap<String, String> headers, String body) {
        this.method = method;
        this.URL = URL;
        this.headers = headers;
        this.body = body;
    }

    public void setMethod(requestMethod method) {
        this.method = method;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
