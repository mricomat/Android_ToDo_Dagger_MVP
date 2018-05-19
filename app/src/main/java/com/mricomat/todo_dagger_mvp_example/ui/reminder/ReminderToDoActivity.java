package com.mricomat.todo_dagger_mvp_example.ui.reminder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mricomat.todo_dagger_mvp_example.R;
import com.mricomat.todo_dagger_mvp_example.ui.reminder.view.ReminderToDoViewFragment;
import com.mricomat.todo_dagger_mvp_example.utils.NavigationUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by mricomat on 10/05/2018.
 */

public class ReminderToDoActivity extends DaggerAppCompatActivity {

    public static final int VIEW_FRAME = R.id.contentFrame;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    ReminderToDoViewFragment mReminderToDoViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fillViews();

        NavigationUtils.navigateToFragment(this, mReminderToDoViewFragment, VIEW_FRAME, false);
    }

    private void fillViews() {
        FloatingActionButton fabButton = findViewById(R.id.floating_action_button);
        fabButton.setVisibility(View.GONE);
        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
