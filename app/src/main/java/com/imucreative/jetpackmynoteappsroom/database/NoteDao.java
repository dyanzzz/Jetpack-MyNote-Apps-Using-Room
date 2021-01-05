package com.imucreative.jetpackmynoteappsroom.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);

    @Update()
    void update(Note note);

    @Delete()
    void delete(Note note);

    //@Query("SELECT * FROM note ORDER BY id ASC")
    @RawQuery(observedEntities = Note.class)
    DataSource.Factory<Integer, Note> getAllNotes(SupportSQLiteQuery query);
    //LiveData<List<Note>> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Note> list);
}
