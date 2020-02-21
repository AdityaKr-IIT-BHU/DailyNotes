package com.adityaandroid.dailynotes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.adityaandroid.dailynotes.database.AppRepository;
import com.adityaandroid.dailynotes.database.NotesEntity;

import java.util.List;


public class MainViewModel extends  AndroidViewModel{


    public LiveData<List<NotesEntity>> mNotes;
    private AppRepository mRepository;



    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotes = mRepository.mNotes;


    }





    public void addSampleData(){
        mRepository.addSampleData();
    }


    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }
}

