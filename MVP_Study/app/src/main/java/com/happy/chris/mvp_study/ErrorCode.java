package com.happy.chris.mvp_study;

public interface ErrorCode {

    public static final int ERR_BASE = -1000;

    public static final int ERR_NO_NETWORK = -1001;
    public static final int ERR_CONNECT_TIMEOUT = -1002;
    public static final int ERR_READ_TIMEOUT = -1003;
    public static final int ERR_CONNECT_FAIL = -1004;
    public static final int ERR_NETWORK_BROKEN = -1005;
    public static final int ERR_TOO_MANY_CMD = -1006;
    
    public static final int ERR_CANCEL = -1020;
    public static final int ERR_DATA_WRONG = -1030;
    public static final int ERR_NOT_SUPPORT_EMULATOR = -1040;
    public static final int ERR_FORCE_BIND = 1113;

    public static final int ERR_QQ_LOGIN = -1050;
    public static final int ERR_WB_LOGIN = -1051;
}
