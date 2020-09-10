package com.saeefmd.noteapphellotask.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saeefmd.noteapphellotask.R;
import com.saeefmd.noteapphellotask.adapter.NoteAdapter;
import com.saeefmd.noteapphellotask.model.Note;
import com.saeefmd.noteapphellotask.viewmodel.NoteViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1001;
    public static final int EDIT_NOTE_REQUEST = 1002;

    private NoteAdapter noteAdapter;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        RecyclerView noteListRv = findViewById(R.id.rv_note_list);
        noteListRv.setLayoutManager(new LinearLayoutManager(this));
        noteListRv.setHasFixedSize(true);

        noteAdapter = new NoteAdapter(NoteListActivity.this);
        noteListRv.setAdapter(noteAdapter);

        FloatingActionButton addNoteFab = findViewById(R.id.fab_add_note);

        addNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteListActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //Update RecycleView
                noteAdapter.submitList(notes);

                int count = notes.size();
                updateNotesCount(count);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));

                Toast.makeText(NoteListActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(noteListRv);

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {

                Intent intent = new Intent(NoteListActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String date = getDate();
        String month = getMonth().toUpperCase();

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0);

            Note note = new Note(title, description, date, month, priority);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Note Can't Be Updated!", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description, date, month, priority);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateNotesCount(int count) {

        SharedPreferences notesCountSp = getSharedPreferences(MainActivity.SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = notesCountSp.edit();

        editor.putInt(MainActivity.TOTAL_NOTES_COUNT, count).apply();
    }

    private String getDate() {

        Date currentDate = Calendar.getInstance().getTime();

        return (String) DateFormat.format("dd",   currentDate);
    }

    private String getMonth() {

        Date currentDate = Calendar.getInstance().getTime();

        return (String) DateFormat.format("MMM",  currentDate);
    }
}