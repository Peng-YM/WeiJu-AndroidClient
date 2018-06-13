package cn.edu.sustc.androidclient.common;

@FunctionalInterface
public interface  ListFilter<T> {
    boolean filter(T src);
}
