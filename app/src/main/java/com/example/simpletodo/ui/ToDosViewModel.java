package com.example.simpletodo.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpletodo.data.ToDo;
import com.example.simpletodo.data.ToDoRepository;

import java.util.ArrayList;

public class ToDosViewModel extends ViewModel {
    private ToDoRepository toDoRepository;

    public ToDosViewModel(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public LiveData<ArrayList<ToDo>> getToDos() {
        return toDoRepository.getToDos();
    }

    public ToDo changeIsDoneValue(ToDo toDo){
        ToDo toDoToModify = toDo.copy();
        toDoToModify.setDone(!toDoToModify.getDone());
        ToDo result = toDoRepository.editToDo(toDoToModify);
        if(result != null){
            return result;
        }
        return toDo;
    }

    public void setToDoSelected(ToDo toDo){
        toDoRepository.setToDoSelected(toDo);
    }
}
