package com.saeefmd.noteapphellotask.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saeefmd.noteapphellotask.R;
import com.saeefmd.noteapphellotask.model.Note;
import com.saeefmd.noteapphellotask.viewmodel.NoteViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFERENCE = "com.saeefmd.noteapphellotask";
    public static final String TOTAL_NOTES_COUNT = "totalNotesCount";

    private TextView totalNotesTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalNotesTv = findViewById(R.id.tv_total_notes);

        TextView welcomeTv = findViewById(R.id.tv_welcome);

        String text = "Welcome John Doe!";
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan fcsName = new ForegroundColorSpan(getResources().getColor(R.color.colorAccent));
        spannableString.setSpan(fcsName, 8, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        welcomeTv.setText(spannableString);

        setNotesCount();

        RelativeLayout notesBox = findViewById(R.id.all_notes_box);

        notesBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteListActivity.class);
                startActivity(intent);
            }
        });

        ImageButton notesIb = findViewById(R.id.ib_notes);
        notesIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setNotesCount() {

        SharedPreferences notesCountSp = getSharedPreferences(MainActivity.SHARED_PREFERENCE, MODE_PRIVATE);

        int totalNotes = notesCountSp.getInt(MainActivity.TOTAL_NOTES_COUNT, 0);

        totalNotesTv.setText(String.valueOf(totalNotes));
    }

    @Override
    protected void onResume() {
        super.onResume();

        setNotesCount();
    }
}