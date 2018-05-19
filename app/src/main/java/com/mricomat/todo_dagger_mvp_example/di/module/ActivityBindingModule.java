package com.mricomat.todo_dagger_mvp_example.di.module;

import com.mricomat.todo_dagger_mvp_example.notifications.ToDoDeleteNotificationService;
import com.mricomat.todo_dagger_mvp_example.ui.add.AddToDoActivity;
import com.mricomat.todo_dagger_mvp_example.ui.home.HomeToDoActivity;
import com.mricomat.todo_dagger_mvp_example.ui.reminder.ReminderToDoActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by mricomat on 03/05/2018.
 */

@Module
public abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = HomeToDoModule.class)
    abstract HomeToDoActivity providesHomeToDoActivity();

    @ContributesAndroidInjector(modules = AddToDoModule.class)
    abstract AddToDoActivity providesAddToDoActivity();

    @ContributesAndroidInjector(modules = ReminderToDoModule.class)
    abstract ReminderToDoActivity providesReminderToDoActivity();
}
