package com.atsir.sh.atscrash;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by tangbin on 2017/6/2.
 */

public class AtsCrash {

    private CrashHandler mCrashHandler;

    private static AtsCrash mInstance;

    private AtsCrash() {

    }

    /**
     * 单例
     * 
     * @return
     */
    private static AtsCrash getInstance() {
        if (mInstance == null) {
            synchronized (AtsCrash.class) {
                if (mInstance == null) {
                    mInstance = new AtsCrash();
                }
            }
        }

        return mInstance;
    }

    /**
     * 初始化
     * 
     * @param crashHandler
     */
    public static void init(CrashHandler crashHandler) {
        getInstance().setCrashHandler(crashHandler);
    }

    private void setCrashHandler(CrashHandler crashHandler) {

        mCrashHandler = crashHandler;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        if (mCrashHandler != null) {
                            // 捕获异常处理
                            mCrashHandler.uncaughtException(
                                    Looper.getMainLooper().getThread(), e);
                        }
                    }
                }
            }
        });

        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        if (mCrashHandler != null) {
                            // 捕获异常处理
                            mCrashHandler.uncaughtException(t, e);
                        }
                    }
                });

    }

    /**
     * 异常回调
     */
    public interface CrashHandler {
        void uncaughtException(Thread t, Throwable e);
    }
}