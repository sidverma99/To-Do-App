package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyHolder> {
    private List<ListData> notesList;
    private Context context;

    public NotesAdapter(List<ListData> notesList, Context context) {
        this.notesList = notesList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ListData listData=notesList.get(position);
        holder.title.setText(listData.getTitle());
        holder.description.setText(listData.getDescription());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView title,description;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListData listData = notesList.get(getAdapterPosition());
                    Intent i=new Intent(context, EditActivity.class);
                    i.putExtra("id",listData.getId());
                    i.putExtra("title",listData.getTitle());
                    i.putExtra("desc",listData.getDescription());
                    context.startActivity(i);
                }
            });
        }
    }
}
