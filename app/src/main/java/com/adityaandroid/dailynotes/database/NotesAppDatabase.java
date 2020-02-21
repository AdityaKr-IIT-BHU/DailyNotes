package com.adityaandroid.dailynotes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {NotesEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class NotesAppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "NotesAppDatabase.db";
    public static volatile NotesAppDatabase instance;
    public static final Object LOCK = new Object();

    public abstract NotesDao notesDao();

    public static NotesAppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            NotesAppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
