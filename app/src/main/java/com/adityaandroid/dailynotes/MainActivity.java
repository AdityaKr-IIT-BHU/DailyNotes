package com.adityaandroid.dailynotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaandroid.dailynotes.Ui.NotesAdapter;
import com.adityaandroid.dailynotes.database.NotesEntity;
import com.adityaandroid.dailynotes.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ViewModelStoreOwner{

    @BindView(R.id.recyclerViewId)
    RecyclerView mRecyclerView;

    @OnClick(R.id.fab)
    void fabclickHandler(){
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private List<NotesEntity> notesData = new ArrayList<>();
    private NotesAdapter mAdapter;
    private MainViewModel mViewModel;
    private ViewModelProvider.AndroidViewModelFactory viewmodelFactory;
    private ViewModelStore viewModelStore = new ViewModelStore();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();

        if(viewmodelFactory == null){
            viewmodelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        }
        mViewModel = new ViewModelProvider(this , viewmodelFactory).get(MainViewModel.class);



        final Observer<List<NotesEntity>> notesObserver =

                new Observer<List<NotesEntity>>(){
                    @Override
                    public void onChanged(List<NotesEntity> notesEntities) {
                        notesData.clear();
                        notesData.addAll(notesEntities);
                        if (mAdapter == null) {
                            mAdapter = new NotesAdapter(notesData, MainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                };

        mViewModel.mNotes.observe(this, notesObserver);

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



    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(itemDecoration);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        }else if(id==R.id.action_delete_all){
            deleteAllNotes();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {
        mViewModel.deleteAllNotes();
    }

    private void addSampleData(){

        mViewModel.addSampleData();
    }
}
