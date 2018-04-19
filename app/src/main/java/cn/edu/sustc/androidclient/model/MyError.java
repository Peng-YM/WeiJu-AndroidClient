package cn.edu.sustc.androidclient.model;

import java.util.ArrayList;

public class MyError {
    ArrayList<ErrorContent> errors;
    static class ErrorContent {
        String title;
        String detail;
        int status;
    }
}
