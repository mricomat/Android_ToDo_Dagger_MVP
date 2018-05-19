package com.mricomat.todo_dagger_mvp_example.ui.home;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.mricomat.todo_dagger_mvp_example.R;
import com.mricomat.todo_dagger_mvp_example.ui.home.view.HomeToDoViewFragment;
import com.mricomat.todo_dagger_mvp_example.utils.NavigationUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class HomeToDoActivity extends DaggerAppCompatActivity {

    private final static int VIEW_FRAME = R.id.contentFrame;

    @Inject
    HomeToDoViewFragment mHomeToDoViewFragment;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();

        NavigationUtils.navigateToFragment(this, mHomeToDoViewFragment, VIEW_FRAME, false);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.app_name));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
