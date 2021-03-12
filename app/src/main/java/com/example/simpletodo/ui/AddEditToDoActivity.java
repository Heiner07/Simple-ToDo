package com.example.simpletodo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.simpletodo.R;
import com.example.simpletodo.data.ToDo;
import com.example.simpletodo.utilities.InjectorHelper;
import com.google.android.material.snackbar.Snackbar;

public class AddEditToDoActivity extends AppCompatActivity {

    private EditText edtToDoValue, edtToDoDetails;
    private LinearLayout llDoneContainer, llEditControls;
    private ImageView imgCheckIsDone;
    private Button btnAdd, btnSave, btnCancel, btnDelete;

    private AddEditToDoViewModel addEditToDoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_to_do);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            // To show the button in the appbar to get back
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initializeUIComponents();
        bindUIEvents();

        if(addEditToDoViewModel.getToDoSelected() == null){
            setUIStateAddToDo();
        }else{
            setUIStateEditToDo();
        }
    }

    private void initializeUIComponents() {
        edtToDoValue = findViewById(R.id.edtToDoValue);
        edtToDoDetails = findViewById(R.id.edtToDoDetails);
        llDoneContainer = findViewById(R.id.llDoneContainer);
        llEditControls = findViewById(R.id.llEditControls);
        imgCheckIsDone = findViewById(R.id.imgCheckIsDone);

        btnAdd = findViewById(R.id.btnAdd);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void bindUIEvents() {
        InjectorHelper injectorHelper = InjectorHelper.getInstance(this);
        addEditToDoViewModel = new ViewModelProvider(
                this,
                injectorHelper.getAddEditToDosViewModelFactory())
                .get(AddEditToDoViewModel.class);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAdd();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ToDo toDoToDelete = addEditToDoViewModel.getToDoSelected();
                final ToDo toDoDeleted = addEditToDoViewModel.deleteToDo(addEditToDoViewModel.getToDoSelected());
                if(toDoDeleted == null || toDoDeleted.getId() != toDoToDelete.getId()){
                    Toast.makeText(AddEditToDoActivity.this, "Error deleting the ToDo", Toast.LENGTH_SHORT).show();
                } else {
                    onBackPressed();
                }
            }
        });

        imgCheckIsDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditToDoViewModel.changeIsDoneValue();
            }
        });

        addEditToDoViewModel.getIsDone().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isChecked) {
                imgCheckIsDone.setImageResource(
                        isChecked ? R.drawable.ic_check_color
                                : R.drawable.ic_check);
            }
        });
    }

    private void setUIStateAddToDo(){
        llDoneContainer.setVisibility(View.GONE);
        llEditControls.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);
    }

    private void setUIStateEditToDo(){
        btnAdd.setVisibility(View.GONE);
        edtToDoValue.setText(addEditToDoViewModel.getToDoSelected().getToDoValue());
        edtToDoDetails.setText(addEditToDoViewModel.getToDoSelected().getDetails());
    }

    private Boolean toDoUIValuesAreCorrect() {
        if (edtToDoValue.getText().toString().isEmpty()) {
            Toast.makeText(this, "Add a ToDo value", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void onClickAdd(){
        if(toDoUIValuesAreCorrect()){
            String toDoValue = edtToDoValue.getText().toString();
            String toDoDetails = edtToDoDetails.getText().toString();
            Boolean result = addEditToDoViewModel.addToDo(new ToDo(0, toDoValue, toDoDetails, false));
            if(result){
                onBackPressed();
            }else{
                Toast.makeText(this, "Error adding ToDo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onClickSave(){
        if(toDoUIValuesAreCorrect()){
            String toDoValue = edtToDoValue.getText().toString();
            String toDoDetails = edtToDoDetails.getText().toString();
            Boolean result = addEditToDoViewModel.editToDo(toDoValue, toDoDetails);
            if(result){
                onBackPressed();
            }else{
                Toast.makeText(this, "Error adding ToDo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}