package com.example.roomdatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.roomdatabase.Room.Note;
import com.example.roomdatabase.Room.NoteDatabase;
import com.example.roomdatabase.adapters.NotesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity implements NotesAdapter.onNoteItemClick {

    // Variables and the widgets
    private TextView textViewMsg;
    private RecyclerView recyclerView;
    private NoteDatabase noteDatabase;
    private List<Note> notes;
    private NotesAdapter notesAdapter;
    private int pos;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Methods:
        initializeViews();
        displayList();

    }

    @Override
    public void onNoteClick(final int pos) {
        // Alert Dialog that will show to the user when FAB is clicked
        // to ask for UPDATE OR DELETE NOTES!
        new AlertDialog.Builder(NoteListActivity.this)
                .setItems(new String[]{"Delete", "Update"}, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                noteDatabase.getNoteData().deleteNote(notes.get(pos));
                                notes.remove(pos);
                                listVisibility();
                                break;

                            case 1:
                                NoteListActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(NoteListActivity.this,
                                                AddNoteActivity.class).putExtra("note", notes.get(pos)),
                                        100);
                                break;
                    }
                }
        }).show();

    }

    private void displayList(){
        noteDatabase = NoteDatabase.getInstance(NoteListActivity.this);
        new RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void, Void, List<Note>>{
        private WeakReference<NoteListActivity> activityReference;

        // the only retain a weak reference to the activity
        RetrieveTask(NoteListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Note> doInBackground(Void... voids) {
            if (activityReference.get() != null){
                return activityReference.get().noteDatabase.getNoteData().getNotes();
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            if ( notes != null && notes.size() > 0 ) {
                activityReference.get().notes.clear();
                activityReference.get().notes.addAll(notes);

                // Hide the empty textView ;
                activityReference.get().textViewMsg.setVisibility(View.GONE);
                activityReference.get().notesAdapter.notifyDataSetChanged();
            }
        }
    }


    private void initializeViews(){

        textViewMsg = findViewById(R.id.tv__empty);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(listener);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(NoteListActivity.this));
        notes = new ArrayList<>();
        notesAdapter = new NotesAdapter(notes, NoteListActivity.this);
        recyclerView.setAdapter(notesAdapter);
    }


    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            startActivityForResult(new Intent(NoteListActivity.this ,
                    AddNoteActivity.class), 100);

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            if (resultCode == 1) {
                notes.add((Note) data.getSerializableExtra("note"));
            } else if (resultCode == 2) {
                notes.set(pos, (Note) data.getSerializableExtra("note"));
            }
            listVisibility();
        }
    }

    private void listVisibility() {
        int emptyMsgVisibilty = View.GONE;
        if (notes.size() == 0 ) {
            // No Items to Display
            if (textViewMsg.getVisibility() == View.GONE)
                emptyMsgVisibilty = View.VISIBLE;
        }
        textViewMsg.setVisibility(emptyMsgVisibilty);
        notesAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        noteDatabase.cleanUp();
        super.onDestroy();
    }
}
