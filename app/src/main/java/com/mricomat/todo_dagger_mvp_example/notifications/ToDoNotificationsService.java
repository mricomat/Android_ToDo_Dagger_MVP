package com.mricomat.todo_dagger_mvp_example.notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mricomat.todo_dagger_mvp_example.R;
import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;
import com.mricomat.todo_dagger_mvp_example.ui.reminder.ReminderToDoActivity;


/**
 * Created by mricomat on 13/04/2018.
 */

public class ToDoNotificationsService extends IntentService {

    public static final String TODO_MODEL_KEY = "todoModelKey";
    public static final String TODO_BUNDLE_KEY = "todoBundleKey";

    public ToDoNotificationsService() {
        super("ToDoNotificationsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        Bundle extras = intent.getBundleExtra(TODO_BUNDLE_KEY);
        extras.setClassLoader(ToDoModel.class.getClassLoader());
        ToDoModel toDoModel = extras.getParcelable(TODO_MODEL_KEY);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Bundle bundle = new Bundle();
        bundle.putString(TODO_MODEL_KEY, toDoModel.getId());

        Intent reminderIntent = new Intent(getApplicationContext(), ReminderToDoActivity.class);
        reminderIntent.putExtra(TODO_MODEL_KEY, toDoModel.getId());

        Intent deleteIntent = new Intent(getApplicationContext(), ToDoDeleteNotificationService.class);
        deleteIntent.putExtra(TODO_MODEL_KEY, toDoModel.getId());

        Notification notification = new Notification.Builder(this)
            .setContentTitle(toDoModel.getToDoText())
            .setSmallIcon(R.drawable.ic_done)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setDeleteIntent(PendingIntent.getService(this, toDoModel.getId().hashCode(),
                deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
            .setContentIntent(PendingIntent.getActivity(this, toDoModel.getId().hashCode(),
                reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT))
            .build();

        manager.notify(100, notification);
    }
}
