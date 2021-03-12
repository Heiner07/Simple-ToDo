package com.example.simpletodo.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.simpletodo.data.ToDoRepository;

public class ToDosViewModelFactory implements ViewModelProvider.Factory {

    private ToDoRepository toDoRepository;

    public ToDosViewModelFactory(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ToDosViewModel(toDoRepository);
    }
}
