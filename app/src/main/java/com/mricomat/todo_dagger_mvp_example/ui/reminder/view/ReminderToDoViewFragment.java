package com.mricomat.todo_dagger_mvp_example.ui.reminder.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.mricomat.todo_dagger_mvp_example.R;
import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;
import com.mricomat.todo_dagger_mvp_example.notifications.NotificationToDoHandler;
import com.mricomat.todo_dagger_mvp_example.ui.reminder.presenter.ReminderToDoPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * Created by mricomat on 11/05/2018.
 */

public class ReminderToDoViewFragment extends DaggerFragment implements ReminderToDoView {

    private static final String TEN_MINUTES = "10 Minutes";

    @BindView(R.id.title)
    TextView mTitleText;

    @BindView(R.id.remove_button)
    Button mRemoveButton;

    @BindView(R.id.spinner_time)
    Spinner mSpinnerTime;

    @Nullable
    @Inject
    String mToDoId;

    @Inject
    ReminderToDoPresenter mPresenter;

    @Inject
    NotificationToDoHandler mNotificationToDoHandler;

    private String mTimeText;

    @Inject
    public ReminderToDoViewFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        View mRootView = inflater.inflate(R.layout.reminder_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        fillViews();
        return mRootView;
    }

    private void fillViews() {
        mTimeText = TEN_MINUTES;
        setListeners();
    }

    private void setListeners() {
        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.removeToDo();
            }
        });

        mSpinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTimeText = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void showToDo(ToDoModel toDoModel) {
        mTitleText.setText(toDoModel.getToDoText());
    }

    @Override
    public void closeReminder() {
        getActivity().finish();
    }

    @Override
    public void createNotification(ToDoModel toDoModel, int requestCode, long timeMillis) {
        mNotificationToDoHandler.createNotification(toDoModel, requestCode, timeMillis);
        closeReminder();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                mPresenter.addNewReminder(mTimeText);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
