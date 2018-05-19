package com.mricomat.todo_dagger_mvp_example.di.module;

import android.support.annotation.Nullable;

import com.mricomat.todo_dagger_mvp_example.ui.add.AddToDoActivity;
import com.mricomat.todo_dagger_mvp_example.ui.add.presenter.AddToDoPresenter;
import com.mricomat.todo_dagger_mvp_example.ui.add.presenter.AddToDoPresenterImpl;
import com.mricomat.todo_dagger_mvp_example.ui.add.view.AddToDoViewFragment;
import com.mricomat.todo_dagger_mvp_example.ui.home.presenter.HomeToDoPresenterImpl;
import com.mricomat.todo_dagger_mvp_example.ui.home.view.HomeToDoViewFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by mricomat on 03/05/2018.
 */

@Module
public abstract class AddToDoModule {
    @ContributesAndroidInjector
    abstract AddToDoViewFragment provideToDoFragment();

    @Binds
    abstract AddToDoPresenter bindHomeToDoPresenter(AddToDoPresenterImpl presenterImpl);

    @Provides
    @Nullable
    static String provideToDoId(AddToDoActivity activity) {
        return activity.getIntent().getStringExtra(HomeToDoPresenterImpl.TODO_ID_KEY);
    }
}
