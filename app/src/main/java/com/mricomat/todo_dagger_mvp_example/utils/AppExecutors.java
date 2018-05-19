package com.mricomat.todo_dagger_mvp_example.utils;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

/**
 * Created by mricomat on 02/05/2018.
 */

@Singleton
public class AppExecutors {

    private Executor mDiskIO;
    private Executor mMainThread;

    private AppExecutors(Executor diskIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        this.mMainThread = mainThread;
    }

    public AppExecutors() {
        this(new DiskIOThreadExecutor(), new MainThreadExecutor());
    }

    public Executor diskIO() {
        return mDiskIO;
    }

    public Executor mainThread() {
        return mMainThread;
    }
}
