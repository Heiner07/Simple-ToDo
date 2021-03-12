package com.example.simpletodo.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletodo.R;
import com.example.simpletodo.data.ToDo;
import com.example.simpletodo.utilities.OnToDoClick;

import java.util.ArrayList;

public class RecyclerViewAdapterToDos extends RecyclerView.Adapter<RecyclerViewAdapterToDos.ViewHolder> {
    private ArrayList<ToDo> toDos = new ArrayList<>();
    private OnToDoClick onToDoClick;
    private OnToDoClick onCheckClick;

    public RecyclerViewAdapterToDos(OnToDoClick onToDoClick, OnToDoClick onCheckClick) {
        this.onToDoClick = onToDoClick;
        this.onCheckClick = onCheckClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_to_do, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ToDo toDo = toDos.get(position);
        holder.textToDoValue.setText(toDo.getToDoValue());
        holder.textToDoDetails.setText(toDo.getDetails());
        if(toDo.getDone()){
            holder.imgCheckIsDone.setImageResource(R.drawable.ic_check_color);
        }else{
            holder.imgCheckIsDone.setImageResource(R.drawable.ic_check);
        }
        holder.rlItemToDoParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToDoClick.onClick(toDo);
            }
        });
        holder.imgCheckIsDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ToDo toDo = toDos.get(position);
                final Boolean result = onCheckClick.onCheckClick(toDo);
                if(result)
                    notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return toDos.size();
    }

    public void setToDos(ArrayList<ToDo> toDos) {
        this.toDos = toDos;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlItemToDoParent;
        private TextView textToDoValue;
        private TextView textToDoDetails;
        private ImageView imgCheckIsDone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlItemToDoParent = itemView.findViewById(R.id.rlItemToDoParent);
            textToDoValue = itemView.findViewById(R.id.textToDoValue);
            textToDoDetails = itemView.findViewById(R.id.textToDoDetails);
            imgCheckIsDone = itemView.findViewById(R.id.imgCheckIsDone);
        }
    }
}