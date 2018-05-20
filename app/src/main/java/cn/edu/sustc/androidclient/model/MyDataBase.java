package cn.edu.sustc.androidclient.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import cn.edu.sustc.androidclient.model.dao.CredentialDao;
import cn.edu.sustc.androidclient.model.dao.UserTaskDao;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.UserTaskRecord;

@Database(
        entities = {
                UserTaskRecord.class,
                Credential.class
        },
        version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    public abstract UserTaskDao userTaskDao();

    public abstract CredentialDao credentialDao();
}
