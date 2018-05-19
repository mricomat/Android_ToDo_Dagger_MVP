package com.mricomat.todo_dagger_mvp_example.ui.home.presenter;

import com.mricomat.todo_dagger_mvp_example.BasePresenter;
import com.mricomat.todo_dagger_mvp_example.ui.home.view.HomeToDoView;

/**
 * Created by mricomat on 02/05/2018.
 */

public interface HomeToDoPresenter extends BasePresenter<HomeToDoView>{

    void loadToDos();

    void getToDos();

    void openToDoScreen(boolean state, String toDoId);

    void result(int requestCode, int resultCode);

    void deleteToDo(String id);

}
