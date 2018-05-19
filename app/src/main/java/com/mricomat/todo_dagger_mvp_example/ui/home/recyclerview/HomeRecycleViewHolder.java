package com.mricomat.todo_dagger_mvp_example.ui.home.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.mricomat.todo_dagger_mvp_example.R;
import com.mricomat.todo_dagger_mvp_example.db.ToDoModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mricomat on 08/03/2018.
 */

public class HomeRecycleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_drawable)
    ImageView mImageTextDrawable;

    @BindView(R.id.to_do_text)
    TextView mToDoText;

    @BindView(R.id.date_text)
    TextView mDateText;


    public HomeRecycleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindModel(ToDoModel toDoModel) {
        mToDoText.setText(toDoModel.getToDoText());
        if (TextUtils.isEmpty(toDoModel.getDate())) {
            mDateText.setVisibility(View.GONE);
        } else {
            mDateText.setVisibility(View.VISIBLE);
        }
        mDateText.setText(toDoModel.getDate());
        if (toDoModel.getColor() != 0) {
            TextDrawable drawable = TextDrawable.builder().buildRound(
                toDoModel.getToDoText().substring(0, 1).toUpperCase(), toDoModel.getColor());
            mImageTextDrawable.setImageDrawable(drawable);
        }
    }
}
