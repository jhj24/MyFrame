package com.zgdj.djframe.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 日志输出
 * Created by user on 2016/3/11.
 */
public class Logs {
    public static String msg = "building_log";
    public static final int DEBUGGER = 0;
    public static final int Demo = 1;
    public static final int STATE = DEBUGGER;

    public static void debug( Object obj) {
        switch (STATE) {
            case DEBUGGER:
                Log.w(msg, String.valueOf(obj));
                break;
            default:
                break;
        }
    }

    public static void debug( Context context, Object obj) {
        switch (STATE) {
            case DEBUGGER:
                msg += context.getClass().getName();
                Log.w(msg, String.valueOf(obj));
                break;
            default:
                break;
        }

    }

    public static void toast( Context context, Object msg) {
        try {
            switch (STATE) {
                case DEBUGGER:
                    Toast.makeText(context, String.valueOf(msg), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
