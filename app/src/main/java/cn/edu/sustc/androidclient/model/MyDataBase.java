package cn.edu.sustc.androidclient.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import cn.edu.sustc.androidclient.model.dao.CollectedImageDao;
import cn.edu.sustc.androidclient.model.dao.CredentialDao;
import cn.edu.sustc.androidclient.model.dao.TaskDao;
import cn.edu.sustc.androidclient.model.dao.UserDao;
import cn.edu.sustc.androidclient.model.dao.UserTaskDao;
import cn.edu.sustc.androidclient.model.data.CollectedImage;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.model.data.UserTaskRecord;

@Database(
        entities = {
                UserTaskRecord.class,
                Credential.class,
                User.class,
                Task.class,
                CollectedImage.class
        },
        version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    public abstract UserTaskDao userTaskDao();
    public abstract CredentialDao credentialDao();
    public abstract TaskDao taskDao();
    public abstract UserDao userDao();
    public abstract CollectedImageDao collectedImageDao();
}
