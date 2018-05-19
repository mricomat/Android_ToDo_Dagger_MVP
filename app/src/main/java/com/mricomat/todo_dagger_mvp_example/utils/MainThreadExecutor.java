package com.mricomat.todo_dagger_mvp_example.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by mricomat on 02/05/2018.
 */

public class MainThreadExecutor implements Executor {

    private Handler mMainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable command) {
        mMainThreadHandler.post(command);
    }
}
