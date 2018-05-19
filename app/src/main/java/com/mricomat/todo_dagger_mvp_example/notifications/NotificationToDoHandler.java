package com.mricomat.todo_dagger_mvp_example.notifications;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;

import javax.inject.Inject;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by mricomat on 10/05/2018.
 */

public class NotificationToDoHandler {

    private Context mContext;

    @Inject
    public NotificationToDoHandler(Context context) {
        mContext = context;
    }

    public void createNotification(ToDoModel toDoModel, int requestCode, long timeMillis) {
        AlarmManager alarmManager = getAlarmManager();
        PendingIntent pendingIntent = PendingIntent.getService(mContext,
            requestCode, buildIntent(toDoModel), PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeMillis, pendingIntent);
    }

    public void deleteNotification(int requestCode) {
        Intent intent = new Intent(mContext, ToDoNotificationsService.class);
        if (isPendingIntentExist(intent, requestCode)) {
            PendingIntent pi = PendingIntent.getService(mContext, requestCode, intent,
                PendingIntent.FLAG_NO_CREATE);
            pi.cancel();
            getAlarmManager().cancel(pi);
        }
    }

    private AlarmManager getAlarmManager() {
        return (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
    }

    private Intent buildIntent(ToDoModel toDoModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ToDoNotificationsService.TODO_MODEL_KEY, toDoModel);
        Intent intent = new Intent(mContext, ToDoNotificationsService.class);
        intent.putExtra(ToDoNotificationsService.TODO_BUNDLE_KEY, bundle);
        return intent;
    }

    private boolean isPendingIntentExist(Intent intent, int requestCode) {
        PendingIntent pi = PendingIntent.getService(mContext, requestCode, intent, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }
}
