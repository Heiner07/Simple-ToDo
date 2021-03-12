package com.example.simpletodo.data;

public class ToDo {
    private int id;
    private String toDoValue;
    private String details;
    private Boolean isDone;

    public ToDo(int id, String toDoValue, String details, Boolean isDone) {
        this.id = id;
        this.toDoValue = toDoValue;
        this.details = details;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToDoValue() {
        return toDoValue;
    }

    public void setToDoValue(String toDoValue) {
        this.toDoValue = toDoValue;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public ToDo copy(){
        return new ToDo(this.id, this.toDoValue, this.details, this.isDone);
    }
}
