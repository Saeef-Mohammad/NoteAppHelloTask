package com.saeefmd.noteapphellotask.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saeefmd.noteapphellotask.R;
import com.saeefmd.noteapphellotask.model.Note;
import com.saeefmd.noteapphellotask.viewmodel.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.saeefmd.noteapphellotask.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.saeefmd.noteapphellotask.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.saeefmd.noteapphellotask.EXTRA_DESCRIPTION";;
    public static final String EXTRA_PRIORITY =
            "com.saeefmd.noteapphellotask.EXTRA_PRIORITY";

    private EditText titleEt;
    private EditText descriptionEt;
    private CheckBox favCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        titleEt = findViewById(R.id.et_title);
        descriptionEt = findViewById(R.id.et_description);

        favCheckBox = findViewById(R.id.cb_favourite);

        TextView headerTv = findViewById(R.id.tv_header);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            headerTv.setText("Edit Note");
            titleEt.setText(intent.getStringExtra(EXTRA_TITLE));
            descriptionEt.setText(intent.getStringExtra(EXTRA_DESCRIPTION));

            int priority = intent.getIntExtra(EXTRA_PRIORITY, 0);

            if (priority == 1) {
                favCheckBox.setChecked(true);
            }
        } else {
            headerTv.setText("Add Note");
        }

        FloatingActionButton fabSave = findViewById(R.id.fab_save);

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveNote();

                Toast.makeText(AddEditNoteActivity.this, "Note Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton backIb = findViewById(R.id.ib_back);
        backIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveNote() {

        String title = titleEt.getText().toString();
        String description = descriptionEt.getText().toString();

        int priority;

        boolean checked = favCheckBox.isChecked();
        if (checked) {
            priority = 1;
        } else {
            priority = 0;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);

        finish();
    }

    private void updateNote() {

    }


}