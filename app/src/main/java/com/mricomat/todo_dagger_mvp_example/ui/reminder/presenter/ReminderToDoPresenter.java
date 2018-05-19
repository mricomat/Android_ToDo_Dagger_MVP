package com.mricomat.todo_dagger_mvp_example.ui.reminder.presenter;

import com.mricomat.todo_dagger_mvp_example.BasePresenter;
import com.mricomat.todo_dagger_mvp_example.ui.reminder.view.ReminderToDoView;

/**
 * Created by mricomat on 11/05/2018.
 */

public interface ReminderToDoPresenter extends BasePresenter<ReminderToDoView> {

    void removeToDo();

    void addNewReminder(String timeText);

}
