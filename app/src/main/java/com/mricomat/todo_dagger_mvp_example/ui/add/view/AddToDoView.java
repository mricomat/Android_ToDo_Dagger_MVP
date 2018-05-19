package com.mricomat.todo_dagger_mvp_example.ui.add.view;

import android.widget.DatePicker;
import android.widget.TimePicker;

import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;

import java.util.Calendar;

/**
 * Created by mricomat on 02/05/2018.
 */

public interface AddToDoView {

    void showToDo(ToDoModel toDoModel);

    void loadDateData(Calendar currentCalendar);

    void showHomeToDo(int resultCode);

    void showDateErrorText();

    void hideDateErrorText();

    void setReminderDateText(DatePicker datePicker);

    void setReminderTimeText(TimePicker timePicker);

    void createNotification(ToDoModel toDoModel, int requestCode, long timeMillis);

    void deleteNotification(int requestCode);
}
