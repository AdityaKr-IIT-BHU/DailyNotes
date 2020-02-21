package com.adityaandroid.dailynotes;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.adityaandroid.dailynotes.database.NotesEntity;
import com.adityaandroid.dailynotes.viewmodel.EditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.adityaandroid.dailynotes.Utility.Constants.EDITING_KEY;
import static com.adityaandroid.dailynotes.Utility.Constants.NOTE_ID_KEY;

public class EditorActivity extends AppCompatActivity implements ViewModelStoreOwner {

    private EditorViewModel editorViewModel;
    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;
    private ViewModelStore viewModelStore = new ViewModelStore();


    @BindView(R.id.note_text)
    TextView mTextView;
    private boolean mNewNote, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        if(savedInstanceState != null){
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }


        if(viewModelFactory == null){
            viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        }
        editorViewModel = new ViewModelProvider(this , viewModelFactory).get(EditorViewModel.class);

        editorViewModel.mLiveNote.observe(this, new Observer<NotesEntity>() {
            @Override
            public void onChanged(NotesEntity notesEntity) {
                if(notesEntity != null && !mEditing){
                   mTextView.setText(notesEntity.getText());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(getString(R.string.new_note));
            mNewNote = true;
        }else {
            setTitle(getString(R.string.edit_note));
            final int noteId = extras.getInt(NOTE_ID_KEY);
            editorViewModel.loadData(noteId);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        viewModelStore.clear();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore(){
        return viewModelStore;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewNote){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            saveAndReturn();
            return true;
        }else if(item.getItemId()==R.id.action_delete){
           editorViewModel.deleteNote();
           finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {

        editorViewModel.saveNote(mTextView.getText().toString());
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }
}
