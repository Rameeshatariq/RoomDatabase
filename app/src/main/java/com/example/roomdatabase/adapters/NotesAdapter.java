package com.example.roomdatabase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.R;
import com.example.roomdatabase.Room.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<Note> list;
    private Context ctx;
    private LayoutInflater layoutInflater;
    private onNoteItemClick onNoteItemClick;

    public NotesAdapter(List<Note> list, Context ctx) {
        layoutInflater = LayoutInflater.from(ctx);
        this.list = list;
        this.ctx = ctx;
        this.onNoteItemClick = (onNoteItemClick) ctx;
    }

    public interface onNoteItemClick {
        void  onNoteClick(int pos);
    }


    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.note_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewTitle.setText(list.get(position).getTitle());
        holder.textViewContent.setText(list.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView textViewContent;
        TextView textViewTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewContent = itemView.findViewById(R.id.item_text);
            textViewTitle = itemView.findViewById(R.id.tv_title);
        }

        @Override
        public void onClick(View v) {
            onNoteItemClick.onNoteClick(getAdapterPosition());
        }
    }
}
