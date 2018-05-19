package com.mricomat.todo_dagger_mvp_example.ui.home.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mricomat.todo_dagger_mvp_example.R;
import com.mricomat.todo_dagger_mvp_example.ui.about.AboutActivity;
import com.mricomat.todo_dagger_mvp_example.ui.add.AddToDoActivity;
import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;
import com.mricomat.todo_dagger_mvp_example.ui.home.recyclerview.HomeItemTouchHelper;
import com.mricomat.todo_dagger_mvp_example.ui.home.recyclerview.HomeRecycleViewAdapter;
import com.mricomat.todo_dagger_mvp_example.ui.home.presenter.HomeToDoPresenter;
import com.mricomat.todo_dagger_mvp_example.ui.home.presenter.HomeToDoPresenterImpl;
import com.mricomat.todo_dagger_mvp_example.utils.NavigationUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * Created by mricomat on 08/05/2018.
 */

public class HomeToDoViewFragment extends DaggerFragment implements HomeToDoView,
    HomeItemTouchHelper.RecyclerItemTouchHelperListener {

    private static final String EMPTY_STRING = "";
    public static final int REQUEST_TODO_CODE = 90;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_container)
    RelativeLayout mEmptyContainer;

    @BindView(R.id.relative_layout)
    RelativeLayout mRelativeLayout;

    @Inject
    HomeToDoPresenter mPresenter;

    private FloatingActionButton mFabButton;
    private HomeRecycleViewAdapter mHomeRecycleViewAdapter;

    @Inject
    public HomeToDoViewFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        fillViews();
        return mRootView;
    }

    private void fillViews() {
        mFabButton = getActivity().findViewById(R.id.floating_action_button);
        mFabButton.setVisibility(View.VISIBLE);
        initSwipeRefreshLayout();
        initRecyclerView();
        setListeners();
    }

    private void setListeners() {
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openToDoScreen(HomeToDoPresenterImpl.NEW_TODO, EMPTY_STRING);
            }
        });
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getToDos();
            }
        });
    }

    private void initRecyclerView() {
        mHomeRecycleViewAdapter = new HomeRecycleViewAdapter();
        mHomeRecycleViewAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = mRecyclerView.getChildAdapterPosition(view);
                mHomeRecycleViewAdapter.getToDos().get(position);
                mPresenter.openToDoScreen(HomeToDoPresenterImpl.UPDATE_TODO, mHomeRecycleViewAdapter
                    .getToDos().get(position).getId());
            }
        });
        mRecyclerView.setAdapter(mHomeRecycleViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new HomeItemTouchHelper(
            0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        String todoId = mHomeRecycleViewAdapter.getToDos().get(position).getId();
        mPresenter.deleteToDo(todoId);
        mHomeRecycleViewAdapter.removeToDo(position);
        if (mHomeRecycleViewAdapter.getToDos().isEmpty()) {
            showEmptyList();
        }
        Snackbar snackbar = Snackbar
            .make(mRelativeLayout, getResources().getString(R.string.generic_removed), Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.GRAY);
        snackbar.show();
    }

    @Override
    public void showToDos(List<ToDoModel> toDos) {
        mHomeRecycleViewAdapter.setToDos(toDos);
    }

    @Override
    public void showToDoScreen(boolean state, Bundle bundleToDoID) {
        Intent intent = new Intent(getContext(), AddToDoActivity.class);
        if (bundleToDoID != null) {
            intent.putExtras(bundleToDoID);
        }
        startActivityForResult(intent, REQUEST_TODO_CODE);
    }

    @Override
    public void showErrorMessage() {
        //
    }

    @Override
    public void showEmptyList() {
        mEmptyContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyList() {
        mEmptyContainer.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingIndicator() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingIndicator() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                NavigationUtils.navigateToActivity(getActivity(), AboutActivity.class, null, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
