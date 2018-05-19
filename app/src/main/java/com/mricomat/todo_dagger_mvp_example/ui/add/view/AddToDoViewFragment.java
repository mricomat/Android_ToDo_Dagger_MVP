package com.mricomat.todo_dagger_mvp_example.ui.add.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mricomat.todo_dagger_mvp_example.R;
import com.mricomat.todo_dagger_mvp_example.notifications.NotificationToDoHandler;
import com.mricomat.todo_dagger_mvp_example.ui.add.presenter.AddToDoPresenter;
import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;
import com.mricomat.todo_dagger_mvp_example.utils.StringsUtils;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * Created by mricomat on 10/05/2018.
 */

public class AddToDoViewFragment extends DaggerFragment implements AddToDoView {

    @BindView(R.id.text_input)
    EditText mTextInput;

    @BindView(R.id.switch_reminder)
    SwitchCompat mSwitchReminder;

    @BindView(R.id.date_input)
    TextView mDateInput;

    @BindView(R.id.hour_input)
    TextView mTimeInput;

    @BindView(R.id.reminder_text)
    TextView mReminderText;

    @BindView(R.id.date_container)
    LinearLayout mDateContainer;

    @BindView(R.id.error_text)
    TextView mErrorText;

    @Nullable
    @Inject
    String mToDoId;

    @Inject
    AddToDoPresenter mPresenter;

    @Inject
    NotificationToDoHandler mNotificationToDoHandler;

    private FloatingActionButton mFabButton;
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private TimePickerDialog.OnTimeSetListener mTimeListener;
    private DatePicker mCurrentDatePicker;
    private TimePicker mCurrentTimePicker;
    private String mReminderDate;
    private String mReminderTime;
    private Calendar mCurrentCalendar;

    @Inject
    public AddToDoViewFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentCalendar = Calendar.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.add_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        fillViews();
        return mRootView;
    }

    private void fillViews() {
        mFabButton = getActivity().findViewById(R.id.floating_action_button);
        mTextInput.clearFocus();
        mFabButton.setImageResource(R.drawable.ic_done);
        mDateContainer.setVisibility(View.GONE);
        mSwitchReminder.setChecked(false);
        setListeners();
    }

    private void setListeners() {
        mSwitchReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check) {
                    mDateContainer.setVisibility(View.VISIBLE);
                } else {
                    mDateContainer.setVisibility(View.GONE);
                }
                mPresenter.setReminderSwitchState(check);
            }
        });

        mDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mErrorText.setVisibility(View.GONE);
                mDatePickerDialog.show();
            }
        });

        mTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mErrorText.setVisibility(View.GONE);
                mTimePickerDialog.show();
            }
        });

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYEar, int dayOfMonth) {
                mPresenter.checkCorrectDate(datePicker, mCurrentTimePicker);
                mDateInput.setText(mReminderDate);
                mReminderText.setText(String.format(getResources().getString(R.string.reminder),
                    mReminderDate, mReminderTime));
            }
        };

        mTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                mPresenter.checkCorrectTime(timePicker, mCurrentDatePicker);
                mTimeInput.setText(mReminderTime);
                mReminderText.setText(String.format(getResources().getString(R.string.reminder),
                    mReminderDate, mReminderTime));
            }
        };

        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDoModel toDoModel = new ToDoModel();
                toDoModel.setToDoText(mTextInput.getText().toString());
                if (mSwitchReminder.isChecked()) {
                    toDoModel.setHasReminder(mSwitchReminder.isChecked());
                    toDoModel.setDate(mReminderDate + StringsUtils.DOUBLE_SPACE + mReminderTime);
                }
                mPresenter.saveToDo(toDoModel, mCurrentDatePicker, mCurrentTimePicker);
            }
        });
    }

    @Override
    public void loadDateData(Calendar calendar) {
        mDatePickerDialog = new DatePickerDialog(getActivity(), mDateListener, mCurrentCalendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        Integer i = calendar.get(Calendar.HOUR_OF_DAY);
        DecimalFormat format = new DecimalFormat(StringsUtils.TWO_ZEROS_FORMAT);
        format.format(i);

        mCurrentDatePicker = new DatePicker(getContext());
        mCurrentTimePicker = new TimePicker(getContext());

        TimePicker hoursAndMinutes = getHoursAndMinutes(calendar);
        mCurrentTimePicker = hoursAndMinutes;

        mTimePickerDialog = new TimePickerDialog(getActivity(), mTimeListener, hoursAndMinutes.getHour(),
            hoursAndMinutes.getMinute(), true);
        mReminderDate = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + StringsUtils.SPACE +
            StringsUtils.capitalizeFirstLetter(new DateFormatSymbols().getMonths()[calendar.get(Calendar.MONTH)]) +
            StringsUtils.SPACE + Integer.toString(calendar.get(Calendar.YEAR));
        mReminderTime = String.valueOf(hoursAndMinutes.getHour()) + StringsUtils.COLON +
            (StringsUtils.formatForTwoDigits(hoursAndMinutes.getMinute()));

        mReminderText.setText(String.format(getResources().getString(R.string.reminder), mReminderDate,
            mReminderTime));

        mDateInput.setText(mReminderDate);
        mTimeInput.setText(mReminderTime);
    }

    private TimePicker getHoursAndMinutes(Calendar calendar) {
        int hours, minutes;
        TimePicker timePicker = new TimePicker(getContext());
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        if (minutes > 55) {
            hours += 1;
            minutes = 0;
        } else {
            minutes += 5;
        }
        timePicker.setMinute(minutes);
        timePicker.setHour(hours);
        return timePicker;
    }

    @Override
    public void showToDo(ToDoModel toDoModel) {
        mTextInput.setText(toDoModel.getToDoText());
        if (!TextUtils.isEmpty(toDoModel.getDate())) {
            mSwitchReminder.setChecked(true);
        }
    }

    @Override
    public void setReminderDateText(DatePicker datePicker) {
        mCurrentDatePicker = datePicker;
        mReminderDate = new StringBuilder().append(Integer.toString(datePicker.getDayOfMonth()))
            .append(StringsUtils.SPACE).append(StringsUtils.capitalizeFirstLetter(
                new DateFormatSymbols().getMonths()[datePicker.getMonth()]))
            .append(StringsUtils.SPACE).append(Integer.toString(datePicker.getYear())).toString();
        hideDateErrorText();
    }

    @Override
    public void setReminderTimeText(TimePicker timePicker) {
        mCurrentTimePicker = timePicker;
        mReminderTime = String.format(StringsUtils.CONCAT_STRINGS, StringsUtils.formatForTwoDigits(
            timePicker.getHour()), StringsUtils.formatForTwoDigits(timePicker.getMinute()));
        hideDateErrorText();
    }

    @Override
    public void createNotification(ToDoModel toDoModel, int requestCode, long timeMillis) {
        mNotificationToDoHandler.createNotification(toDoModel, requestCode, timeMillis);
    }

    @Override
    public void deleteNotification(int requestCode) {
        mNotificationToDoHandler.deleteNotification(requestCode);
    }

    @Override
    public void showHomeToDo(int resultCode) {
        getActivity().setResult(resultCode);
        getActivity().finish();
    }

    @Override
    public void showDateErrorText() {
        mErrorText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDateErrorText() {
        mErrorText.setVisibility(View.GONE);
    }
}
