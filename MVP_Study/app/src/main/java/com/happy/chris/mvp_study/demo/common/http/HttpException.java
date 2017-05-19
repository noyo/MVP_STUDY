package com.happy.chris.mvp_study.demo.common.http;


import java.util.ArrayList;
import java.util.List;

/**
 * package: com.happy.chris.mvp_study.demo.common.http
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/16.
 */

public class HttpException {

    public final static int ERROR_HTTP_FAILED = 1;
    
    int mErrorCode;
    Throwable mThrowable;

    public static List<HttpException> mExceptions = new ArrayList<>();

    public HttpException(int errorCode) {
        mErrorCode = errorCode;
        mExceptions.add(this);
    }
    
    public HttpException(Throwable throwable, int errorCode) {
        mThrowable = throwable;
        mErrorCode = errorCode;
        mExceptions.add(this);
    }
    
    public int getErrorCode() {
        return mErrorCode;
    }
}
