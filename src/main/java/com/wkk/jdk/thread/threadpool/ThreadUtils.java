package com.wkk.jdk.thread.threadpool;

import java.text.MessageFormat;

/**
 * @author weikunkun
 * @since 2021/4/11
 */
public class ThreadUtils {
    public static String stackTrace(StackTraceElement[] stackTrace) {
        if (stackTrace != null && stackTrace.length > 0) {
            StringBuilder logs = new StringBuilder(512);
            for (StackTraceElement e : stackTrace) {
                logs.append(MessageFormat.format("{0}: {1}(): {2}" , e.getClassName(), e.getMethodName(),
                        e.getLineNumber())).append("\n");
            }
            return logs.toString();
        }
        return "";
    }
}
