package com.alon.androiddevtool.taskrunner;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alon.androiddevtool.converters.MapConverter;
import com.alon.androiddevtool.models.SharedPreferencesField;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetSPContentTask extends BaseTask {

    private final iOnDataFetched listener;//listener in fragment that shows and hides ProgressBar
    private final Context context;
    private final String fileName;


    public GetSPContentTask(iOnDataFetched listener, Context context, String fileName) {
        this.listener = listener;
        this.context = context;
        this.fileName = fileName;
    }

    @Override
    public Object call() throws Exception {
        Map<String, ?> fileContent = new HashMap<>();
        MapConverter mapConverter = new MapConverter();
        ArrayList<SharedPreferencesField> spData = new ArrayList<>();
        File sp_dir = new File(context.getApplicationInfo().dataDir, "shared_prefs");
        if (sp_dir.exists() && sp_dir.isDirectory()) {
            String[] list = sp_dir.list();
            for (int i = 0; i < list.length; i++) {
                if (list[i].equals(fileName)) {
                    String sp_name = list[i].substring(0, list[i].length() - 4);
                    SharedPreferences sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
                    fileContent = sp.getAll();
                    spData = mapConverter.fromMap(fileContent);
                    return spData;
                }
            }
        }
        return spData;
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
