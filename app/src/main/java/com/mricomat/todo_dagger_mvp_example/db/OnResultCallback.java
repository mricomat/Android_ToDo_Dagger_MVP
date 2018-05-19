package com.mricomat.todo_dagger_mvp_example.db;

/**
 * Created by mricomat on 14/03/2018.
 */

public interface OnResultCallback<T> {

    void onDataLoaded(T response);

    void onDataNotAvailable();

}
