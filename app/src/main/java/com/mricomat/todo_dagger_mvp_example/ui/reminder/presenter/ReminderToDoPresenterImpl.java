package com.mricomat.todo_dagger_mvp_example.ui.reminder.presenter;

import android.support.annotation.Nullable;

import com.mricomat.todo_dagger_mvp_example.db.OnResultCallback;
import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;
import com.mricomat.todo_dagger_mvp_example.db.ToDoRepository;
import com.mricomat.todo_dagger_mvp_example.ui.reminder.view.ReminderToDoView;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by mricomat on 11/05/2018.
 */

public class ReminderToDoPresenterImpl implements ReminderToDoPresenter {

    private static final String TEN_MINUTES_STR = "10 Minutes";
    private static final String THIRTY_MINUTES_STR = "30 Minutes";
    private static final String ONE_HOUR_STR = "1 Hour";
    private static final String TWO_HOURS_STR = "2 Hours";
    private static final int TEN_MINUTES_NUM = 10;
    private static final int THIRTY_MINUTES_NUM = 30;
    private static final int ONE_HOUR_NUM = 1;
    private static final int TWO_HOURS_NUM = 2;

    @Nullable
    private String mToDoId;

    private ToDoRepository mToDoRepository;
    private ReminderToDoView mView;
    private ToDoModel mToDoModel;

    @Inject
    public ReminderToDoPresenterImpl(ToDoRepository toDoRepository, @Nullable String toDoId) {
        this.mToDoRepository = toDoRepository;
        this.mToDoId = toDoId;
    }

    @Override
    public void takeView(ReminderToDoView view) {
        mView = view;
        getToDo();
    }

    @Override
    public void removeToDo() {
        mToDoRepository.deleteToDo(mToDoId);
        mView.closeReminder();
    }

    @Override
    public void addNewReminder(String timeText) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        switch (timeText) {
            case TEN_MINUTES_STR:
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + TEN_MINUTES_NUM);
                break;
            case THIRTY_MINUTES_STR:
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + THIRTY_MINUTES_NUM);
                break;
            case ONE_HOUR_STR:
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + ONE_HOUR_NUM);
                break;
            case TWO_HOURS_STR:
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + TWO_HOURS_NUM);
                break;
            default:
                break;
        }
        mView.createNotification(mToDoModel, mToDoId.hashCode(), calendar.getTimeInMillis());
    }

    @Override
    public void dropView() {
        mView = null;
    }

    private void getToDo() {
        mToDoRepository.getToDo(mToDoId, new OnResultCallback<ToDoModel>() {
            @Override
            public void onDataLoaded(ToDoModel response) {
                mToDoModel = response;
                mView.showToDo(response);
            }

            @Override
            public void onDataNotAvailable() {
                //
            }
        });
    }
}
