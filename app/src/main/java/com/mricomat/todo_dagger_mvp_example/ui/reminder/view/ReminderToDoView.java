package com.mricomat.todo_dagger_mvp_example.ui.reminder.view;

import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;

/**
 * Created by mricomat on 11/05/2018.
 */

public interface ReminderToDoView {

    void showToDo(ToDoModel toDoModel);

    void closeReminder();

    void createNotification(ToDoModel toDoModel, int requestCode, long timeMillis);

}
