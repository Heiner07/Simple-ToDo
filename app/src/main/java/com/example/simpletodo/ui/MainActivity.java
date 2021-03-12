package com.example.simpletodo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletodo.R;
import com.example.simpletodo.data.ToDo;
import com.example.simpletodo.utilities.InjectorHelper;
import com.example.simpletodo.utilities.OnToDoClick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvToDos;
    private FloatingActionButton fabAddToDo;
    private ToDosViewModel toDosViewModel;
    private ScrollView svContentOfToDos;
    private TextView textNoToDos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIComponents();
        bindUIEvents();
    }

    private void initializeUIComponents() {
        svContentOfToDos = findViewById(R.id.svContentOfToDos);
        rvToDos = findViewById(R.id.rvToDos);
        textNoToDos = findViewById(R.id.textNoToDos);
        fabAddToDo = findViewById(R.id.fabAddToDo);
    }

    private void bindUIEvents() {
        InjectorHelper injectorHelper = InjectorHelper.getInstance(this);
        toDosViewModel = new ViewModelProvider(
                this,
                injectorHelper.getToDosViewModelFactory())
                .get(ToDosViewModel.class);

        final RecyclerViewAdapterToDos adapter = new RecyclerViewAdapterToDos(onToDoClick, onCheckClick);

        rvToDos.setAdapter(adapter);
        rvToDos.setLayoutManager(new LinearLayoutManager(this));
        toDosViewModel.getToDos().observe(this, new Observer<ArrayList<ToDo>>() {
            @Override
            public void onChanged(ArrayList<ToDo> toDos) {
                if (toDos.isEmpty()) {
                    textNoToDos.setVisibility(View.VISIBLE);
                    svContentOfToDos.setVisibility(View.INVISIBLE);
                } else {
                    textNoToDos.setVisibility(View.INVISIBLE);
                    svContentOfToDos.setVisibility(View.VISIBLE);
                }
                adapter.setToDos(toDos);
            }
        });

        fabAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDosViewModel.setToDoSelected(null);
                Intent intent = new Intent(MainActivity.this, AddEditToDoActivity.class);
                startActivity(intent);
            }
        });
    }

    private OnToDoClick onToDoClick = new OnToDoClick() {
        @Override
        public void onClick(ToDo toDo) {
            toDosViewModel.setToDoSelected(toDo);
            Intent intent = new Intent(MainActivity.this, AddEditToDoActivity.class);
            startActivity(intent);
        }

        @Override
        public Boolean onCheckClick(ToDo toDo) {
            return true;
        }
    };

    private OnToDoClick onCheckClick = new OnToDoClick() {
        @Override
        public void onClick(ToDo toDo) {
        }

        @Override
        public Boolean onCheckClick(ToDo toDo) {
            ToDo result = toDosViewModel.changeIsDoneValue(toDo);
            if (result == toDo) {
                Toast.makeText(MainActivity.this, "Error updating the ToDo", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    };
}