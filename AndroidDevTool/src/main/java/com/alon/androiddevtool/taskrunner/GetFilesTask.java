package com.alon.androiddevtool.taskrunner;

import android.content.Context;

import java.io.File;


public class GetFilesTask extends BaseTask {
    private final iOnDataFetched listener;//listener in fragment that shows and hides ProgressBar
    private final Context context;
    private final File file;

    public GetFilesTask(iOnDataFetched onDataFetchedListener, Context context, File file) {
        this.listener = onDataFetchedListener;
        this.context = context;
        this.file = file;
    }

    @Override
    public Object call() throws Exception {
        File[] files = file.listFiles();
        if (files == null) {
            return new File[0];
        }
        return files;
    }

    @Override
    public void setUiForLoading() {
        listener.showProgressBar();
    }

    @Override
    public void setDataAfterLoading(Object result) {
        listener.hideProgressBar();
        listener.setDataInPageWithResult(result);
    }
}
