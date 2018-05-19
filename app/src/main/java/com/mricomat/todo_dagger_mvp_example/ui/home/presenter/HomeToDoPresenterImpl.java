package com.mricomat.todo_dagger_mvp_example.ui.home.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.mricomat.todo_dagger_mvp_example.db.OnResultCallback;
import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;
import com.mricomat.todo_dagger_mvp_example.db.ToDoRepository;
import com.mricomat.todo_dagger_mvp_example.ui.home.view.HomeToDoView;
import com.mricomat.todo_dagger_mvp_example.ui.home.view.HomeToDoViewFragment;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by mricomat on 02/05/2018.
 */

public class HomeToDoPresenterImpl implements HomeToDoPresenter {

    public static final boolean UPDATE_TODO = false;
    public static final boolean NEW_TODO = true;
    public static final String TODO_ID_KEY = "TODO_ID";

    private HomeToDoView mView;
    private ToDoRepository mToDoRepository;
    private List<ToDoModel> mToDos;
    private boolean mFirstLoad = true;

    @Inject
    public HomeToDoPresenterImpl(ToDoRepository toDoRepository) {
        this.mToDoRepository = toDoRepository;
    }

    @Override
    public void takeView(HomeToDoView view) {
        mView = view;
        loadToDos();
    }

    @Override
    public void loadToDos() {
        mView.showLoadingIndicator();
        if (mFirstLoad) {
            getToDos();
        } else {
            mView.showToDos(mToDos);
            mView.hideLoadingIndicator();
            mFirstLoad = false;
        }
    }

    @Override
    public void getToDos() {
        mToDoRepository.getToDos(new OnResultCallback<List<ToDoModel>>() {
            @Override
            public void onDataLoaded(List<ToDoModel> response) {
                Collections.reverse(response);
                mToDos = response;
                mView.showToDos(mToDos);
                if(mToDos == null || mToDos.isEmpty()) {
                    mView.showEmptyList();
                } else {
                    mView.hideEmptyList();
                }
                mView.hideLoadingIndicator();
                mFirstLoad = false;
            }

            @Override
            public void onDataNotAvailable() {
                // This will be called if the table is new or just empty
                if (mToDos != null) {
                    mToDos.clear();
                }
                mView.showToDos(mToDos);
                mView.showEmptyList();
                mView.hideLoadingIndicator();
            }
        });
    }

    @Override
    public void openToDoScreen(boolean state, String toDoId) {
        if(mView != null) {
            Bundle extras = new Bundle();
            if (!state) {
                extras.putString(TODO_ID_KEY, toDoId);
            }
            mView.showToDoScreen(state, extras);
        }
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (resultCode == Activity.RESULT_OK && requestCode == HomeToDoViewFragment.REQUEST_TODO_CODE) {
            getToDos();
        }
    }

    @Override
    public void deleteToDo(String id) {
        mToDoRepository.deleteToDo(String.valueOf(id));
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
