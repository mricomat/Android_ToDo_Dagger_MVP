package com.mricomat.todo_dagger_mvp_example;

/**
 * Created by mricomat on 14/05/2018.
 */

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();

}
