package com.mricomat.todo_dagger_mvp_example.db;

import com.mricomat.todo_dagger_mvp_example.utils.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mricomat on 02/05/2018.
 */

@Singleton
public class ToDoRepository implements ToDoDataSource {

    private ToDoDao mToDoDao;
    private AppExecutors mAppExecutors;

    @Inject
    public ToDoRepository(ToDoDao toDoDao, AppExecutors appExecutors) {
        this.mToDoDao = toDoDao;
        this.mAppExecutors = appExecutors;
    }

    @Override
    public void getToDos(final OnResultCallback<List<ToDoModel>> onResult) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<ToDoModel> toDos = mToDoDao.getToDos();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (toDos.isEmpty()) {
                            // This will be called if the table is new or just empty
                            onResult.onDataNotAvailable();
                        } else {
                            onResult.onDataLoaded(toDos);
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getToDo(final String id, final OnResultCallback<ToDoModel> onResult) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final ToDoModel toDo = mToDoDao.getToDoById(id);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (toDo != null) {
                            onResult.onDataLoaded(toDo);
                        } else {
                            onResult.onDataNotAvailable();
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveToDo(final ToDoModel toDo) {
        if (toDo != null) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    mToDoDao.addToDo(toDo);
                }
            };
            mAppExecutors.diskIO().execute(runnable);
        }
    }

    @Override
    public void update(final ToDoModel toDo) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mToDoDao.updateToDo(toDo);
                mToDoDao.getToDoById(String.valueOf(toDo.getId()));
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllToDos() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mToDoDao.deleteToDos();
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteToDo(final String id) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mToDoDao.deleteToDoById(id);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }
}
