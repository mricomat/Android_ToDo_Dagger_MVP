package com.mricomat.todo_dagger_mvp_example.db;

import java.util.List;

/**
 * Created by mricomat on 12/03/2018.
 */

public interface ToDoDataSource {

    void getToDos(OnResultCallback<List<ToDoModel>> onResult);

    void getToDo(String id, OnResultCallback<ToDoModel> onResult);

    void saveToDo(ToDoModel toDo);

    void update(ToDoModel toDo);

    void deleteAllToDos();

    void deleteToDo(String id);
}
