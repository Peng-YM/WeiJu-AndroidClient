package cn.edu.sustc.androidclient.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import cn.edu.sustc.androidclient.model.dao.UserTaskDao;
import cn.edu.sustc.androidclient.model.data.UserTaskRecord;

@Database(entities = {UserTaskRecord.class}, version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    public abstract UserTaskDao userTaskDao();
}
