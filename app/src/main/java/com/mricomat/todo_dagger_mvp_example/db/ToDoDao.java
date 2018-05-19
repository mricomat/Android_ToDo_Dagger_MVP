package com.mricomat.todo_dagger_mvp_example.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by mricomat on 02/05/2018.
 */

@Dao
public interface ToDoDao {

    @Query("SELECT * FROM ToDo")
    List<ToDoModel> getToDos();

    @Query("SELECT * FROM ToDo WHERE id = :toDoId")
    ToDoModel getToDoById(String toDoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToDo(ToDoModel toDo);

    @Update
    void updateToDo(ToDoModel toDo);

    @Query("DELETE FROM ToDo WHERE id = :toDoId")
    int deleteToDoById(String toDoId);

    @Query("DELETE FROM ToDo")
    void deleteToDos();

}