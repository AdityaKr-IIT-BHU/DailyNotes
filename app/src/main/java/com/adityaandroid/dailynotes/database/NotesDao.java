package com.adityaandroid.dailynotes.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NotesEntity notesEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NotesEntity> notes);

    @Delete
    void deleteNote(NotesEntity notesEntity);

    @Query("SELECT * FROM notes WHERE id=:id")
    NotesEntity getNoteById(int id);

    @Query("SELECT * FROM notes ORDER BY date DESC")
    LiveData<List<NotesEntity>> getAll();

    @Query("DELETE FROM notes")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM notes")
    int getCount();


}
