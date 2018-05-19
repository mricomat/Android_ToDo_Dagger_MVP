package com.mricomat.todo_dagger_mvp_example.ui.add.presenter;

import android.widget.DatePicker;
import android.widget.TimePicker;

import com.mricomat.todo_dagger_mvp_example.BasePresenter;
import com.mricomat.todo_dagger_mvp_example.ui.add.view.AddToDoView;
import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;

/**
 * Created by mricomat on 02/05/2018.
 */

public interface AddToDoPresenter extends BasePresenter<AddToDoView> {

    void saveToDo(ToDoModel toDoModel, DatePicker currentDatePicker, TimePicker currentTimePicker);

    void getToDo(String toDoId);

    void setReminderSwitchState(boolean state);

    void checkCorrectDate(DatePicker datePicker, TimePicker currentTimePicker);

    void checkCorrectTime(TimePicker timePicker, DatePicker currentDatePicker);

}
