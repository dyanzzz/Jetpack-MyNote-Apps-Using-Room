package com.imucreative.jetpackmynoteappsroom.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;
import com.imucreative.jetpackmynoteappsroom.R;
import com.imucreative.jetpackmynoteappsroom.database.Note;
import com.imucreative.jetpackmynoteappsroom.databinding.ActivityMainBinding;
import com.imucreative.jetpackmynoteappsroom.helper.SortUtils;
import com.imucreative.jetpackmynoteappsroom.ui.insert.NoteAddUpdateActivity;
import com.imucreative.jetpackmynoteappsroom.viewmodel.ViewModelFactory;

import java.util.List;

import static com.imucreative.jetpackmynoteappsroom.ui.insert.NoteAddUpdateActivity.REQUEST_UPDATE;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NotePagedListAdapter adapter;
    //private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainViewModel mainViewModel = obtainViewModel(MainActivity.this);
        mainViewModel.getAllNotes(SortUtils.NEWEST).observe(this, noteObserver);

        //adapter = new NoteAdapter(MainActivity.this);
        adapter = new NotePagedListAdapter(MainActivity.this);

        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNotes.setHasFixedSize(true);
        binding.rvNotes.setAdapter(adapter);
        binding.fabAdd.setOnClickListener(view -> {
            if (view.getId() == R.id.fab_add) {
                Intent intent = new Intent(MainActivity.this, NoteAddUpdateActivity.class);
                startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_ADD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == NoteAddUpdateActivity.REQUEST_ADD) {
                if (resultCode == NoteAddUpdateActivity.RESULT_ADD) {
                    showSnackbarMessage(getString(R.string.added));
                }
            } else if (requestCode == REQUEST_UPDATE) {
                if (resultCode == NoteAddUpdateActivity.RESULT_UPDATE) {
                    showSnackbarMessage(getString(R.string.changed));
                } else if (resultCode == NoteAddUpdateActivity.RESULT_DELETE) {
                    showSnackbarMessage(getString(R.string.deleted));
                }
            }
        }
    }

    @NonNull
    private static MainViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }

    private final Observer<PagedList<Note>> noteObserver = new Observer<PagedList<Note>>() {
        @Override
        public void onChanged(@Nullable PagedList<Note> noteList) {
            if (noteList != null) {
                //adapter.setListNotes(noteList);
                adapter.submitList(noteList);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String sort = "";
        switch (item.getItemId()) {
            case R.id.action_newest:
                sort = SortUtils.NEWEST;
                break;
            case R.id.action_oldest:
                sort = SortUtils.OLDEST;
                break;
            case R.id.action_random:
                sort = SortUtils.RANDOM;
                break;
        }
        obtainViewModel(MainActivity.this).getAllNotes(sort).observe(this, noteObserver);
        item.setChecked(true);
        return super.onOptionsItemSelected(item);
    }
}
