package com.mricomat.todo_dagger_mvp_example.di.module;

import com.mricomat.todo_dagger_mvp_example.notifications.ToDoDeleteNotificationService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by mricomat on 14/05/2018.
 */

@Module
public abstract class NotificationServiceBindingModule {
    @ContributesAndroidInjector()
    abstract ToDoDeleteNotificationService providesDeleteNotificationService();
}
