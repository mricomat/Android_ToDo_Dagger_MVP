package com.mricomat.todo_dagger_mvp_example;

import com.mricomat.todo_dagger_mvp_example.db.ToDoRepository;
import com.mricomat.todo_dagger_mvp_example.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by mricomat on 02/05/2018.
 */

public class ToDoApplication extends DaggerApplication {

    @Inject
    ToDoRepository mToDoRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    public ToDoRepository getToDoRepository() {
        return mToDoRepository;
    }
}
