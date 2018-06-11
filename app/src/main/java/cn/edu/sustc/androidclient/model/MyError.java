package cn.edu.sustc.androidclient.model;

import java.util.ArrayList;

public class MyError {
    public ArrayList<ErrorContent> errors;

    static class ErrorContent {
        public String title;
        public String detail;
        public int status;
    }
}
