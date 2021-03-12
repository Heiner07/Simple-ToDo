package com.example.simpletodo.utilities;

import android.content.Context;

import com.example.simpletodo.ui.AddEditToDoViewModelFactory;
import com.example.simpletodo.ui.ToDosViewModelFactory;
import com.example.simpletodo.data.DatabaseHelper;
import com.example.simpletodo.data.ToDoRepository;
import com.example.simpletodo.data.ToDoService;

public class InjectorHelper {
    private static InjectorHelper instance;
    private ToDoRepository toDoRepository;
    private ToDoService toDoService;
    private static DatabaseHelper databaseHelper;

    private InjectorHelper(){
        getToDoService();
        getToDoRepository();
    }

    public static InjectorHelper getInstance(Context context){
        if(databaseHelper == null){
            databaseHelper = DatabaseHelper.getDatabaseInstance(context);
        }
        if(instance == null){
            instance = new InjectorHelper();
        }
        return instance;
    }

    public ToDosViewModelFactory getToDosViewModelFactory(){
        return new ToDosViewModelFactory(getToDoRepository());
    }

    public AddEditToDoViewModelFactory getAddEditToDosViewModelFactory(){
        return new AddEditToDoViewModelFactory(getToDoRepository());
    }

    public ToDoRepository getToDoRepository() {
        if(toDoRepository == null){
            toDoRepository = new ToDoRepository(getToDoService());
        }
        return toDoRepository;
    }

    public ToDoService getToDoService() {
        if(toDoService == null){
            toDoService = new ToDoService(databaseHelper);
        }
        return toDoService;
    }
}
