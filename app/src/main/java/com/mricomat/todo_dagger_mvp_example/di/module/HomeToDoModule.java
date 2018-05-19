package com.mricomat.todo_dagger_mvp_example.di.module;

import com.mricomat.todo_dagger_mvp_example.ui.home.presenter.HomeToDoPresenter;
import com.mricomat.todo_dagger_mvp_example.ui.home.presenter.HomeToDoPresenterImpl;
import com.mricomat.todo_dagger_mvp_example.ui.home.view.HomeToDoViewFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by mricomat on 03/05/2018.
 */

@Module
public abstract class HomeToDoModule {
    @ContributesAndroidInjector
    abstract HomeToDoViewFragment provideToDoFragment();

    @Binds
    abstract HomeToDoPresenter bindHomeToDoPresenter(HomeToDoPresenterImpl presenterImpl);
}
