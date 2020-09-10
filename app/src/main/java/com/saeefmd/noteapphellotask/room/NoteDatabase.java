package com.saeefmd.noteapphellotask.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.saeefmd.noteapphellotask.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;
        private PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Client Meeting", "For technical revisions", "08", "JUN", 0));
            noteDao.insert(new Note("Website design", "UI design for e-commerce", "13", "MAY", 0));
            noteDao.insert(new Note("Logo design", "Logo concept for e-commerce", "20", "APR", 0));
            noteDao.insert(new Note("Product branding", "Findings new brands", "24", "AUG", 0));
            noteDao.insert(new Note("Cooking recipe's", "1/2 pork adobo, 1/2 cup sugar", "08", "JUN", 0));
            return null;
        }
    }
}