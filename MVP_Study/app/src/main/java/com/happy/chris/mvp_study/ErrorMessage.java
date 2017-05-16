package com.happy.chris.mvp_study;

import android.annotation.SuppressLint;

import java.util.HashMap;

@SuppressLint("UseSparseArrays")
public class ErrorMessage {

    private static final String ERR_BASE = "出现错误，请重试";
    private static final String ERR_NO_NETWORK = "请检查您的网络是否可用";
    private static final String ERR_CONNECT_TIMEOUT = "连接失败，请重试";
    private static final String ERR_READ_TIMEOUT = "读取数据超时";
    private static final String ERR_CONNECT_FAIL = "连接失败，您检查您的网络设置";
    private static final String ERR_NETWORK_BROKEN = "出现网络异常，您检查您的网络";
    private static final String ERR_TOO_MANY_CMD = "操作太快，请稍等";
    private static final String ERR_NOT_SUPPORT_EMULATOR = "暂不支持模拟器，请使用真机！";
    
    private static final HashMap<Integer, String> emap = new HashMap<Integer, String>();

    static {
        emap.put(ErrorCode.ERR_BASE, ERR_BASE);
        emap.put(ErrorCode.ERR_NO_NETWORK, ERR_NO_NETWORK);
        emap.put(ErrorCode.ERR_CONNECT_TIMEOUT, ERR_CONNECT_TIMEOUT);
        emap.put(ErrorCode.ERR_READ_TIMEOUT, ERR_READ_TIMEOUT);
        emap.put(ErrorCode.ERR_CONNECT_FAIL, ERR_CONNECT_FAIL);
        emap.put(ErrorCode.ERR_NETWORK_BROKEN, ERR_NETWORK_BROKEN);
        emap.put(ErrorCode.ERR_TOO_MANY_CMD, ERR_TOO_MANY_CMD);
        emap.put(ErrorCode.ERR_NOT_SUPPORT_EMULATOR, ERR_NOT_SUPPORT_EMULATOR);
    }
    
    public static String getErrorMessage(int errorCode) {
        String msg = emap.get(errorCode);
        if (msg == null) {
            return ERR_BASE;
        }
        return msg;
    }
}
