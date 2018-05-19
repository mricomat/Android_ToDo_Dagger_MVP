package com.mricomat.todo_dagger_mvp_example.di.component;

import android.app.Application;

import com.mricomat.todo_dagger_mvp_example.ToDoApplication;
import com.mricomat.todo_dagger_mvp_example.db.ToDoRepository;
import com.mricomat.todo_dagger_mvp_example.di.module.ActivityBindingModule;
import com.mricomat.todo_dagger_mvp_example.di.module.AppModule;
import com.mricomat.todo_dagger_mvp_example.di.module.NotificationServiceBindingModule;
import com.mricomat.todo_dagger_mvp_example.di.module.ToDoRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by mricomat on 02/05/2018.
 */

@Singleton
@Component(modules = {ToDoRepositoryModule.class,
    ActivityBindingModule.class,
    NotificationServiceBindingModule.class,
    AppModule.class,
    AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<ToDoApplication> {
    ToDoRepository getToDoRepository();

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
