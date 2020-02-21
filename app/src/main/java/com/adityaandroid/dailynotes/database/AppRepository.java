package com.adityaandroid.dailynotes.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.adityaandroid.dailynotes.Utility.SampleData;


import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<NotesEntity>>  mNotes;
    private NotesAppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context){
        if (ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {

       mDb = NotesAppDatabase.getInstance(context);
       mNotes = getAllNotes();
    }
    public void addSampleData(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.notesDao().insertAll(SampleData.getNotes());
            }
        });

    }

    private LiveData<List<NotesEntity>> getAllNotes(){
        return mDb.notesDao().getAll();
    }

    public void deleteAllNotes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.notesDao().deleteAll();
            }
        });
    }

    public NotesEntity getNoteById(final int noteId) {
        return mDb.notesDao().getNoteById(noteId);
    }

    public void insertNote(final NotesEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.notesDao().insertNote(note);
            }
        });
    }

    public void deleteNote( final NotesEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.notesDao().deleteNote(note);
            }
        });

    }
}
