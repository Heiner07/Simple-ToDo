package com.example.simpletodo.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.simpletodo.data.ToDoRepository;

public class AddEditToDoViewModelFactory implements ViewModelProvider.Factory {

    private ToDoRepository toDoRepository;

    public AddEditToDoViewModelFactory(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddEditToDoViewModel(toDoRepository);
    }
}
