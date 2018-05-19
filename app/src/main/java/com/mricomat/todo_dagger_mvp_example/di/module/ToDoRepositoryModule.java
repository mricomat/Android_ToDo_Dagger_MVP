package com.mricomat.todo_dagger_mvp_example.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.mricomat.todo_dagger_mvp_example.db.ToDoDao;
import com.mricomat.todo_dagger_mvp_example.db.ToDoDatabase;
import com.mricomat.todo_dagger_mvp_example.db.ToDoRepository;
import com.mricomat.todo_dagger_mvp_example.utils.AppExecutors;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mricomat on 03/05/2018.
 */

@Module
public class ToDoRepositoryModule {
    @Provides
    @Singleton
    ToDoDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
            ToDoDatabase.class, "ToDo.db")
            .build();
    }

    @Provides
    @Singleton
    ToDoDao provideUserDao(ToDoDatabase database) {
        return database.toDoDao();
    }

    @Provides
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    @Provides
    @Singleton
    ToDoRepository providesToDoRepository(ToDoDao toDoDao, AppExecutors appExecutors) {
        return new ToDoRepository(toDoDao, appExecutors);
    }
}
