package com.imucreative.jetpackmynoteappsroom.ui.main;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.imucreative.jetpackmynoteappsroom.database.Note;
import com.imucreative.jetpackmynoteappsroom.repository.NoteRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private NoteRepository mNoteRepository;

    public MainViewModel(Application application) {
        mNoteRepository = new NoteRepository(application);
    }

    LiveData<List<Note>> getAllNotes() {
        return mNoteRepository.getAllNotes();
    }
}
