package com.adityaandroid.dailynotes;

import android.content.Context;
import static org.junit.Assert.*;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.adityaandroid.dailynotes.Utility.SampleData;
import com.adityaandroid.dailynotes.database.NotesAppDatabase;
import com.adityaandroid.dailynotes.database.NotesDao;
import com.adityaandroid.dailynotes.database.NotesEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private NotesAppDatabase mDb;
    private NotesDao mDao;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context,
                NotesAppDatabase.class).build();

        mDao = mDb.notesDao();
        Log.i(TAG, "createDb: ");
    }

    @After
    public void closeDb(){
        mDb.close();
        Log.i(TAG, "closeDb: ");
    }

    @Test
    public void createAndRetrieveNotes(){
        mDao.insertAll(SampleData.getNotes());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveNotes: " +count);
        assertEquals(SampleData.getNotes().size(), count);
    }

    @Test
    public void compareString(){
        mDao.insertAll(SampleData.getNotes());
        NotesEntity original = SampleData.getNotes().get(0);
        NotesEntity fromDb = mDao.getNoteById(1);
        assertEquals(original.getText(), fromDb.getText());

    }
}
