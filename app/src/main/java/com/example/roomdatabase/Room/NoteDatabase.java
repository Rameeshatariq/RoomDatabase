package com.example.roomdatabase.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Note.class}, version = 1)
@TypeConverters({DataRoomConverter.class})
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao getNoteData();
    private static NoteDatabase noteDB;


    public static NoteDatabase getInstance(Context context){
        if(null == noteDB){
            noteDB = buildDatabaseInstance(context);
        }
        return noteDB;
    }

    private static NoteDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                NoteDatabase.class,
                Constants.Database_Name).allowMainThreadQueries().build();
    }

    public void cleanUp(){
        noteDB.cleanUp();
    }
}
