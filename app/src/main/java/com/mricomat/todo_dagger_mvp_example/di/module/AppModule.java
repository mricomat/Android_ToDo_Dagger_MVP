package com.mricomat.todo_dagger_mvp_example.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by mricomat on 02/05/2018.
 */

@Module
public abstract class AppModule {
    @Binds
    abstract Context bindContext(Application application);
}
