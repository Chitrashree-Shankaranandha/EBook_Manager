package com.example.navigationdrawerexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    List<Comment> List;

    public CommentAdapter(Context context, java.util.List<Comment> list) {
        this.context = context;
        this.List = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Comment comment = List.get(position);
        holder.comment.setText(comment.getComment());
        holder.username.setText(comment.getPublisher());
        holder.bookname.setText(comment.getBookname());
        holder.date.setText(comment.getTime());

    }

    @Override
    public int getItemCount() {
        return List.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView comment,username,date,bookname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            comment=itemView.findViewById(R.id.comment);
            username=itemView.findViewById(R.id.username);
            date=itemView.findViewById(R.id.date);
            bookname=itemView.findViewById(R.id.bookname);

        }
    }

}

