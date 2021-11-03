package com.example.roomdatabase.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = Constants.Table_Name_Note)

public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long note_id;

    @ColumnInfo(name = "note_content")
    private String content;

    private String title;

    private Date date;

    public Note(String content, String title) {
        this.content = content;
        this.title = title;
        this.date = new Date(System.currentTimeMillis());
    }

    @Ignore
    public Note(){

    }

    public Long getNote_id() {
        return note_id;
    }

    public void setNote_id(Long note_id) {
        this.note_id = note_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return
                Objects.equals(note_id, note.note_id) && Objects.equals(content, note.content)
                        && Objects.equals(title, note.title) &&
                        Objects.equals(date, note.date);
    }

    @Override
    public int hashCode() {
        int result = (int) note_id.intValue();
        result = 31*result + (title != null ? title.hashCode() : 0);
        return Objects.hash(note_id, content, title, date);
    }

    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + note_id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
