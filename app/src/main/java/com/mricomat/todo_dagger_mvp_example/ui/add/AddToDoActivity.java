package com.mricomat.todo_dagger_mvp_example.ui.add;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mricomat.todo_dagger_mvp_example.R;
import com.mricomat.todo_dagger_mvp_example.ui.add.view.AddToDoViewFragment;
import com.mricomat.todo_dagger_mvp_example.utils.NavigationUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by mricomat on 02/05/2018.
 */

public class AddToDoActivity extends DaggerAppCompatActivity {

    private final static int VIEW_FRAME = R.id.contentFrame;

    @Inject
    AddToDoViewFragment mAddToDoViewFragment;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();

        NavigationUtils.navigateToFragment(this, mAddToDoViewFragment, VIEW_FRAME, false);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.new_todo));
            actionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
