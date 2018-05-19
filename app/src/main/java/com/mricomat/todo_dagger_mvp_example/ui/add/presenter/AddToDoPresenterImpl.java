package com.mricomat.todo_dagger_mvp_example.ui.add.presenter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.mricomat.todo_dagger_mvp_example.ui.add.view.AddToDoView;
import com.mricomat.todo_dagger_mvp_example.db.OnResultCallback;
import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;
import com.mricomat.todo_dagger_mvp_example.db.ToDoRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by mricomat on 02/05/2018.
 */

public class AddToDoPresenterImpl implements AddToDoPresenter {

    private final static String DATE_FORMAT = "dd MMM yyyy HH:mm";
    private static final String EMPTY_STRING = "";

    private AddToDoView mView;
    private ToDoRepository mToDoRepository;
    private ToDoModel mToDoModel;
    private boolean mIsNewToDo = true;
    private boolean mIsSwitchChecked = false;

    @Nullable
    private String mToDoId;

    @Inject
    public AddToDoPresenterImpl(ToDoRepository toDoRepository, @Nullable String toDoId) {
        this.mToDoRepository = toDoRepository;
        this.mToDoId = toDoId;
        mIsNewToDo = TextUtils.isEmpty(toDoId);
    }

    @Override
    public void takeView(AddToDoView view) {
        mView = view;
        if (mIsNewToDo) {
            mView.loadDateData(Calendar.getInstance());
        } else {
            getToDo(mToDoId);
        }
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void saveToDo(ToDoModel toDoModel, DatePicker currentDatePicker, TimePicker currentTimePicker) {
        if (mIsNewToDo) {
            toDoModel.setColor(ColorGenerator.MATERIAL.getRandomColor());
            toDoModel.setId(UUID.randomUUID().toString());
            if (mIsSwitchChecked) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(currentDatePicker.getYear(), currentDatePicker.getMonth(),
                    currentDatePicker.getDayOfMonth(), currentTimePicker.getHour(), currentTimePicker.getMinute());
                if (!toDoModel.getToDoText().isEmpty()) {
                    mView.createNotification(toDoModel, toDoModel.getId().hashCode(), calendar.getTimeInMillis());
                }
            }
            saveNewToDo(toDoModel);
        } else {
            if (mIsSwitchChecked) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(currentDatePicker.getYear(), currentDatePicker.getMonth(),
                    currentDatePicker.getDayOfMonth(), currentTimePicker.getHour(), currentTimePicker.getMinute());
                mView.deleteNotification(mToDoModel.getId().hashCode());
                mView.createNotification(toDoModel, toDoModel.getId().hashCode(), calendar.getTimeInMillis());
            }
            toDoModel.setColor(mToDoModel.getColor());
            toDoModel.setId(mToDoModel.getId());
            updateToDo(toDoModel);
        }
    }

    private void saveNewToDo(ToDoModel toDoModel) {
        int resultCode = 12;
        if (!toDoModel.getToDoText().equals(EMPTY_STRING)) {
            mToDoRepository.saveToDo(toDoModel);
            resultCode = Activity.RESULT_OK;
        }
        mView.showHomeToDo(resultCode);
    }

    private void updateToDo(ToDoModel toDoModel) {
        int resultCode = 12;
        if (!toDoModel.getToDoText().equals(EMPTY_STRING)) {
            mToDoRepository.update(toDoModel);
            resultCode = Activity.RESULT_OK;
        }
        mView.showHomeToDo(resultCode);
    }

    @Override
    public void getToDo(String toDoId) {
        mToDoRepository.getToDo(toDoId, new OnResultCallback<ToDoModel>() {
            @Override
            public void onDataLoaded(ToDoModel response) {
                mView.showToDo(response);
                mToDoModel = response;
                Calendar calendar = Calendar.getInstance();
                if (!TextUtils.isEmpty(response.getDate())) {
                    calendar.setTime(formatStringToDate(response.getDate()));
                }
                mView.loadDateData(calendar);
            }

            @Override
            public void onDataNotAvailable() {
                // This will be called if is null
            }
        });
    }

    private Date formatStringToDate(String stringDate) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            date = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public void setReminderSwitchState(boolean state) {
        mIsSwitchChecked = state;
    }

    @Override
    public void checkCorrectDate(DatePicker datePicker, TimePicker currentTimePicker) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        if ((currentCalendar.before(selectedCalendar) || isCalendarsEquals(currentCalendar, selectedCalendar)) &&
            (!isCalendarsEquals(currentCalendar, selectedCalendar) || (currentTimePicker.getHour() >=
                currentCalendar.get(Calendar.HOUR_OF_DAY) && (currentTimePicker.getHour() !=
                currentCalendar.get(Calendar.HOUR_OF_DAY) || (currentTimePicker.getMinute() >
                currentCalendar.get(Calendar.MINUTE)))))) {
            mView.setReminderDateText(datePicker);
        } else {
            mView.showDateErrorText();
        }
    }

    @Override
    public void checkCorrectTime(TimePicker timePicker, DatePicker currentDatePicker) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(currentDatePicker.getYear(), currentDatePicker.getMonth(), currentDatePicker.getDayOfMonth());
        if ((currentCalendar.before(selectedCalendar) || isCalendarsEquals(currentCalendar, selectedCalendar)) &&
            (!isCalendarsEquals(currentCalendar, selectedCalendar) || ((currentCalendar.get(Calendar.HOUR_OF_DAY) <=
                timePicker.getHour()) && ((currentCalendar.get(Calendar.HOUR_OF_DAY) != timePicker.getHour()) ||
                (currentCalendar.get(Calendar.MINUTE) < timePicker.getMinute()))))) {
            mView.setReminderTimeText(timePicker);
        } else {
            mView.showDateErrorText();
        }
    }

    private boolean isCalendarsEquals(Calendar calendar_one, Calendar calendar_two) {
        return calendar_one.get(Calendar.YEAR) == calendar_two.get(Calendar.YEAR) &&
            (calendar_one.get(Calendar.MONTH) == calendar_two.get(Calendar.MONTH) &&
                calendar_one.get(Calendar.DAY_OF_MONTH) == calendar_two.get(Calendar.DAY_OF_MONTH));
    }
}
