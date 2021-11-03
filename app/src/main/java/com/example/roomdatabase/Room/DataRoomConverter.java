package com.example.roomdatabase.Room;

import androidx.room.TypeConverter;

import java.util.Date;

public class DataRoomConverter {
    @TypeConverter
    public static Date toDate(Long value){
        return  value == null ? null : new Date(value);

    }

    @TypeConverter
    public static  Long toDate(Date value){
        return  value == null ? null : value.getTime();
    }
}
