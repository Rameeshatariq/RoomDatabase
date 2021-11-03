package com.example.roomdatabase.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.xml.sax.SAXNotRecognizedException;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("Select * FROM "+Constants.Table_Name_Note)
    List<Note> getNotes();

    @Insert
    long insertNote(Note note);

    @Update
    void updateNote(Note repos);

    @Delete
    void  deleteNote(Note note);

    @Delete
    void deleteNotes (Note... note);


}
