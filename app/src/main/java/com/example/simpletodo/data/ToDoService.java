package com.example.simpletodo.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ToDoService {
    private DatabaseHelper databaseHelper;
    public final static String TO_DO_TABLE = "ToDos";
    public final static String PRIMARY_KEY_TO_DO_TABLE = "id";
    public final static String TO_DO_VALUE_COLUMN = "toDoValue";
    public final static String TO_DO_DETAILS_COLUMN = "toDoDetails";
    public final static String TO_DO_IS_DONE_COLUMN = "isDone";

    public ToDoService(DatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
    }

    public ArrayList<ToDo> getAllToDos(){
        final ArrayList<ToDo> returnToDos = new ArrayList<>();

        final SQLiteDatabase db = databaseHelper.getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT * FROM " + TO_DO_TABLE,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String toDoValue = cursor.getString(1);
                String toDoDetails = cursor.getString(2);
                Boolean isDone = cursor.getInt(3) > 0;
                returnToDos.add(new ToDo(id, toDoValue, toDoDetails, isDone));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnToDos;
    }

    public ToDo get(int id){
        ToDo toDoFound = null;
        final SQLiteDatabase db = databaseHelper.getReadableDatabase();
        final Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TO_DO_TABLE +
                        " WHERE " + PRIMARY_KEY_TO_DO_TABLE + " = " + id,
                null);
        if(cursor.moveToFirst()){
            int toDoId = cursor.getInt(0);
            String toDoValue = cursor.getString(1);
            String toDoDetails = cursor.getString(2);
            Boolean isDone = cursor.getInt(3) > 0;
            toDoFound = new ToDo(toDoId, toDoValue, toDoDetails, isDone);
        }
        cursor.close();
        db.close();
        return toDoFound;
    }

    public ToDo add(ToDo toDo){
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        final ContentValues cv = new ContentValues();

        cv.put(TO_DO_VALUE_COLUMN, toDo.getToDoValue());
        cv.put(TO_DO_DETAILS_COLUMN, toDo.getDetails());
        cv.put(TO_DO_IS_DONE_COLUMN, toDo.getDone());

        final long result = db.insert(TO_DO_TABLE, null, cv);
        db.close();

        if(result != -1){
            toDo.setId((int)result);
            return toDo;
        }else{
            return null;
        }
    }

    public ToDo update(ToDo toDo){
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        final ContentValues cv = new ContentValues();

        cv.put(PRIMARY_KEY_TO_DO_TABLE, toDo.getId());
        cv.put(TO_DO_VALUE_COLUMN, toDo.getToDoValue());
        cv.put(TO_DO_DETAILS_COLUMN, toDo.getDetails());
        cv.put(TO_DO_IS_DONE_COLUMN, toDo.getDone());

        final int result = db.update(TO_DO_TABLE,
                cv,
                PRIMARY_KEY_TO_DO_TABLE + " = ?",
                new String[]{ "" + toDo.getId() });
        db.close();
        if(result > 0){
            return toDo;
        }
        return null;
    }

    public ToDo delete(int id){
        final ToDo toDoForDeleted = get(id);
        if(toDoForDeleted != null){
            final SQLiteDatabase db = databaseHelper.getWritableDatabase();
            final int result = db.delete(TO_DO_TABLE,
                    PRIMARY_KEY_TO_DO_TABLE +" = ?",
                    new String[]{ String.valueOf(id) });
            db.close();
            if(result > 0){
                return toDoForDeleted;
            }
        }
        return null;
    }
}
