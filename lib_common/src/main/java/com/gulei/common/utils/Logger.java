package com.gulei.common.utils;

import android.util.Log;

import com.gulei.lib_common.BuildConfig;

/**
 * 简单日志类
 */

public class Logger {

    public static final String TAG = "aaa";
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数
    static boolean SaveLog = false;//是否保存日志到本地
    static String LOGFILENAME = "cake_log.txt";//本地日志文件名

    private Logger() {
        /* Protect from instantiations */
    }

    /**
     * Android的BuildConfig有一个很合适的DEBUG可以用，它在你发布release版本，这个bool值自动变为false;所以我们可以利用这一点，重新定义写Log的方法：
     *
     * @return
     */
    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    //去掉TAG：我们可以用类名来作为TAG的内容,获取方法名和行数：
    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public static void e(int tag, String message) {
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());


        Log.e(TAG, createLog(message));
    }

    public static void e(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        String logMsg = createLog(message);
        if (logMsg.length() > 4000) {
            for (int i = 0; i < logMsg.length(); i += 4000) {
                if (i + 4000 < logMsg.length())
                    Log.e(TAG, logMsg.substring(i, i + 4000));
                else Log.e(TAG, logMsg.substring(i, logMsg.length()));
            }
        } else {
            Log.e(TAG, logMsg);
        }
    }


    public static void i(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(TAG, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(TAG, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(TAG, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(TAG, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(TAG, createLog(message));
    }

}
