package com.example.simpletodo.utilities;

import com.example.simpletodo.data.ToDo;

public interface OnToDoClick {
    void onClick(ToDo toDo);
    Boolean onCheckClick(ToDo toDo);
}
