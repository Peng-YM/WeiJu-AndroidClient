package cn.edu.sustc.androidclient.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import cn.edu.sustc.androidclient.model.converters.AnnotationTagListConverter;
import cn.edu.sustc.androidclient.model.converters.TaskFormatterConverter;
import cn.edu.sustc.androidclient.model.dao.AnnotationCommitDao;
import cn.edu.sustc.androidclient.model.dao.CollectedImageDao;
import cn.edu.sustc.androidclient.model.dao.CredentialDao;
import cn.edu.sustc.androidclient.model.dao.TaskDao;
import cn.edu.sustc.androidclient.model.dao.TransactionDao;
import cn.edu.sustc.androidclient.model.dao.UserDao;
import cn.edu.sustc.androidclient.model.data.AnnotationCommits;
import cn.edu.sustc.androidclient.model.data.CollectedImage;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.model.data.User;

@Database(
        entities = {
                User.class,
                Transaction.class,
                Task.class,
                Credential.class,
                CollectedImage.class,
                AnnotationCommits.class
        },
        version = 1, exportSchema = false)
@TypeConverters({
        TaskFormatterConverter.class,
        AnnotationTagListConverter.class
})
public abstract class MyDataBase extends RoomDatabase {
    public abstract AnnotationCommitDao annotationCommitDao();
    public abstract CollectedImageDao collectedImageDao();
    public abstract CredentialDao credentialDao();
    public abstract TaskDao taskDao();
    public abstract TransactionDao transactionDao();
    public abstract UserDao userDao();
}
