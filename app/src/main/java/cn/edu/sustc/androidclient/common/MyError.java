package cn.edu.sustc.androidclient.common;

import java.util.ArrayList;

public class MyError {
    ArrayList<ErrorContent> errors;
    static class ErrorContent {
        String title;
        String detail;
        int status;
    }
}
