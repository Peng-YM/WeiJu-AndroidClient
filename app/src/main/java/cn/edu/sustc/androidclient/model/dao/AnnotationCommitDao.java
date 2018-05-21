package cn.edu.sustc.androidclient.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import cn.edu.sustc.androidclient.model.data.AnnotationCommits;

@Dao
public interface AnnotationCommitDao {
    @Insert
    void commit(AnnotationCommits... commits);
}
