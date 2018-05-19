package com.mricomat.todo_dagger_mvp_example.di.module;

import android.support.annotation.Nullable;

import com.mricomat.todo_dagger_mvp_example.notifications.ToDoNotificationsService;
import com.mricomat.todo_dagger_mvp_example.ui.reminder.ReminderToDoActivity;
import com.mricomat.todo_dagger_mvp_example.ui.reminder.presenter.ReminderToDoPresenter;
import com.mricomat.todo_dagger_mvp_example.ui.reminder.presenter.ReminderToDoPresenterImpl;
import com.mricomat.todo_dagger_mvp_example.ui.reminder.view.ReminderToDoViewFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by mricomat on 11/05/2018.
 */

@Module
public abstract class ReminderToDoModule {
    @ContributesAndroidInjector
    abstract ReminderToDoViewFragment provideToDoFragment();

    @Binds
    abstract ReminderToDoPresenter bindHomeToDoPresenter(ReminderToDoPresenterImpl presenterImpl);

    @Provides
    @Nullable
    static String provideToDoId(ReminderToDoActivity activity) {
        return activity.getIntent().getStringExtra(ToDoNotificationsService.TODO_MODEL_KEY);
    }
}
