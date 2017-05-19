package com.happy.chris.mvp_study.demo.common.http;

/**
 * package: com.happy.chris.mvp_study.demo.common.http
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/16.
 */

public class HttpException extends Exception {
    
    public final int mErrorCode;
    
    public HttpException(int errorCode) {
        mErrorCode = errorCode;
    }
    
    public HttpException(Throwable throwable, int errorCode) {
        super(throwable);
        mErrorCode = errorCode;
    }
    
    public int getErrorCode() {
        return mErrorCode;
    }
}
