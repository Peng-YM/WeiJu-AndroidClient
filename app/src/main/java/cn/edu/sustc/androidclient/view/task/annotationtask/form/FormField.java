package cn.edu.sustc.androidclient.view.task.annotationtask.form;

import android.text.Layout;

import java.util.List;

import io.reactivex.annotations.Nullable;

public abstract class FormField {
    public FormField(String title, @Nullable String subtitle, Layout layout){

    }
    abstract List<String> getValues();
}
