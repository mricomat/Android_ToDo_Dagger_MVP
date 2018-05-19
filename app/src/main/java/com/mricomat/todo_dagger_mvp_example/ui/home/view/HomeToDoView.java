package com.mricomat.todo_dagger_mvp_example.ui.home.view;

import android.os.Bundle;

import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;

import java.util.List;

/**
 * Created by mricomat on 02/05/2018.
 */

public interface HomeToDoView {

    void showToDos(List<ToDoModel> toDos);

    void showToDoScreen(boolean state, Bundle bundleToDoId);

    void showErrorMessage();

    void showEmptyList();

    void hideEmptyList();

    void showLoadingIndicator();

    void hideLoadingIndicator();

}
