package com.adityaandroid.dailynotes.Ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaandroid.dailynotes.EditorActivity;
import com.adityaandroid.dailynotes.R;
import com.adityaandroid.dailynotes.database.NotesEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.adityaandroid.dailynotes.Utility.Constants.NOTE_ID_KEY;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final List<NotesEntity> mNotes;
    private final Context mContext;

    public NotesAdapter(List<NotesEntity> mNotes, Context mContext) {
        this.mNotes = mNotes;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notes_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
      final NotesEntity note = mNotes.get(position);
      holder.mTextView.setText(note.getText());

      holder.mFab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(mContext, EditorActivity.class);
              intent.putExtra(NOTE_ID_KEY, note.getId());
              mContext.startActivity(intent);
          }
      });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.note_text)
        TextView mTextView;

        @BindView(R.id.fab)
        FloatingActionButton mFab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
