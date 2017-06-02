package com.atsir.sh.atscrashdemo;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.atsir.sh.atscrash.AtsCrash;

/**
 * Created by tangbin on 2017/6/2.
 */
public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AtsCrash.init(new AtsCrash.CrashHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                // e.printStackTrace();
                Log.d("atangsir", Log.getStackTraceString(e));
                showToast(e.getMessage());

            }
        });
    }

    private void showToast(final String text) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}