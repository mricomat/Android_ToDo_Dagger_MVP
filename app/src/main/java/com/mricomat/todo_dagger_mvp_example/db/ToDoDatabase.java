package com.mricomat.todo_dagger_mvp_example.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by mricomat on 02/05/2018.
 */

@Database(entities = {ToDoModel.class}, version = 1)
public abstract class ToDoDatabase extends RoomDatabase {

    public abstract ToDoDao toDoDao();

}
