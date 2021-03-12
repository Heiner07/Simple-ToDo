package com.example.simpletodo.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpletodo.data.ToDo;
import com.example.simpletodo.data.ToDoRepository;

public class AddEditToDoViewModel extends ViewModel {
    private ToDoRepository toDoRepository;
    private ToDo toDoSelected;
    private MutableLiveData<Boolean> isDone;

    public AddEditToDoViewModel(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
        this.toDoSelected = this.toDoRepository.getToDoSelected();
        this.isDone = new MutableLiveData<>();
        if(this.toDoSelected != null){
            this.isDone.setValue(this.toDoSelected.getDone());
        }
    }

    public Boolean addToDo(ToDo toDo){
        final ToDo result = toDoRepository.addToDo(toDo);
        return result != null;
    }

    public Boolean editToDo(String toDoValue, String toDoDetails){
        this.toDoSelected.setToDoValue(toDoValue);
        this.toDoSelected.setDetails(toDoDetails);
        this.toDoSelected.setDone(this.isDone.getValue());
        final ToDo result = toDoRepository.editToDo(this.toDoSelected);
        return result != null;
    }

    public ToDo deleteToDo(ToDo toDo){
        return this.toDoRepository.deleteToDo(toDo);
    }

    public ToDo getToDoSelected() {
        return toDoSelected;
    }

    public LiveData<Boolean> getIsDone(){
        return this.isDone;
    }

    public void changeIsDoneValue(){
        if(isDone.getValue() != null)
            isDone.setValue(!isDone.getValue());
    }
}
