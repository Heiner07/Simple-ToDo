package com.example.simpletodo.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class ToDoRepository {
    private ToDoService toDoService;
    private MutableLiveData<ArrayList<ToDo>> toDos;
    private ToDo toDoSelected;

    public ToDoRepository(ToDoService toDoService){
        this.toDoService = toDoService;
        this.toDoSelected = null;
    }

    private void initializeToDos(){
        if(toDos == null){
            toDos = new MutableLiveData<>();
            loadAllToDos();
        }
    }

    public LiveData<ArrayList<ToDo>> getToDos() {
        initializeToDos();
        return toDos;
    }

    public void loadAllToDos(){
        toDos.setValue(toDoService.getAllToDos());
    }

    public ToDo addToDo(ToDo toDo){
        initializeToDos();
        final ArrayList<ToDo> tempList = toDos.getValue();

        ToDo toDoAdded = toDoService.add(toDo);
        if(toDoAdded != null && tempList != null){
            tempList.add(toDoAdded);
            toDos.setValue(tempList);
            return toDoAdded;
        }

        return null;
    }

    public ToDo editToDo(ToDo toDo){
        initializeToDos();
        final ArrayList<ToDo> tempList = toDos.getValue();
        ToDo toDoUpdated = toDoService.update(toDo);
        if(toDoUpdated != null && tempList != null){
            final int size = tempList.size();
            for(int i = 0; i < size; i++){
                if(tempList.get(i).getId() == toDo.getId()){
                    tempList.set(i, toDoUpdated);
                    toDos.setValue(tempList);
                    break;
                }
            }
            return toDoUpdated;
        }

        return null;
    }

    public ToDo deleteToDo(ToDo toDo){
        initializeToDos();
        final ArrayList<ToDo> tempList = toDos.getValue();
        ToDo toDoDeleted = toDoService.delete(toDo.getId());
        if(toDoDeleted != null && tempList != null){
            final int size = tempList.size();
            for(int i = 0; i < size; i++){
                if(tempList.get(i).getId() == toDo.getId()){
                    tempList.remove(i);
                    toDos.setValue(tempList);
                    break;
                }
            }
            return toDoDeleted;
        }

        return null;
    }

    public void setToDoSelected(ToDo toDo){
        this.toDoSelected = toDo == null ? null : toDo.copy();
    }

    public ToDo getToDoSelected(){
        return this.toDoSelected;
    }
}
