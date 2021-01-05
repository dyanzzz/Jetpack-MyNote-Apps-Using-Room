package com.imucreative.jetpackmynoteappsroom.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.imucreative.jetpackmynoteappsroom.database.Note;
import com.imucreative.jetpackmynoteappsroom.database.NoteDao;
import com.imucreative.jetpackmynoteappsroom.database.NoteRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    private NoteDao mNotesDao;
    private ExecutorService executorService;

    public NoteRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        mNotesDao = db.noteDao();
    }

    public LiveData<List<Note>> getAllNotes() {
        return mNotesDao.getAllNotes();
    }

    public void insert(final Note note) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mNotesDao.insert(note);
            }
        });
    }

    public void delete(final Note note){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mNotesDao.delete(note);
            }
        });
    }

    public void update(final Note note){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mNotesDao.update(note);
            }
        });
    }

}
