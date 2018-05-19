package com.mricomat.todo_dagger_mvp_example.ui.home.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mricomat.todo_dagger_mvp_example.R;
import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;

import java.util.List;

/**
 * Created by mricomat on 08/03/2018.
 */

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<HomeRecycleViewHolder> implements View.OnClickListener {

    private List<ToDoModel> mToDos;
    private View.OnClickListener mOnClickListener;

    @Override
    public HomeRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.to_do_item, parent, false);
        view.setOnClickListener(this);
        return new HomeRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeRecycleViewHolder holder, int position) {
        holder.bindModel(mToDos.get(position));
    }

    @Override
    public int getItemCount() {
        return mToDos == null ? 0 : mToDos.size();
    }

    @Override
    public void onClick(View view) {
        mOnClickListener.onClick(view);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setToDos(List<ToDoModel> toDos) {
        mToDos = toDos;
        notifyDataSetChanged();
    }

    public void removeToDo(int position) {
        mToDos.remove(position);
        notifyItemRemoved(position);
    }

    public List<ToDoModel> getToDos() {
        return mToDos;
    }
}
