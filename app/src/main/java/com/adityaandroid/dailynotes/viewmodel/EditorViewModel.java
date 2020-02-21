package com.adityaandroid.dailynotes.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.adityaandroid.dailynotes.database.AppRepository;
import com.adityaandroid.dailynotes.database.NotesEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<NotesEntity> mLiveNote=
            new MutableLiveData<>();

    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int noteId) {

      executor.execute(new Runnable() {
          @Override
          public void run() {
              NotesEntity note = mRepository.getNoteById(noteId);
              mLiveNote.postValue(note);
          }
      });
    }

    public void saveNote(String noteText) {
        NotesEntity note = mLiveNote.getValue();

        if(note == null){
            if(TextUtils.isEmpty(noteText.trim())){
                return;
            }
            note = new NotesEntity(new Date(), noteText.trim());

        }else{
            note.setText(noteText.trim());
        }
        mRepository.insertNote(note);
    }

    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
