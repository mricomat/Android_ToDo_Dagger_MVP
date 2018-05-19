package com.mricomat.todo_dagger_mvp_example.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.mricomat.todo_dagger_mvp_example.db.ToDoRepository;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;

/**
 * Created by mricomat on 13/04/2018.
 */

public class ToDoDeleteNotificationService extends DaggerIntentService {

    @Inject
    ToDoRepository mToDoRepository;

    public ToDoDeleteNotificationService() {
        super("ToDoDeleteNotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        String toDoId = intent.getStringExtra(ToDoNotificationsService.TODO_MODEL_KEY);
        mToDoRepository.deleteToDo(toDoId);
    }
}
