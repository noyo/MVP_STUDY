package com.happy.chris.mvp_study.demo.common.http;

import okhttp3.OkHttpClient;

/**
 * package: com.happy.chris.uestcliblogin.common.http
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/10.
 */

public class OkHttpWrapper {
    
    private final static Byte[] LOCK_BYTE = new Byte[0];
    private static OkHttpWrapper mInstance;
    private OkHttpClient mOkClient;
    
    private OkHttpWrapper() {
        mOkClient = new OkHttpClient();
    }
    
    public static OkHttpWrapper getInstance() {
        if (mInstance == null) {
            synchronized (LOCK_BYTE) {
                if (mInstance == null) {
                    mInstance = new OkHttpWrapper();
                }
            }
        }
        return mInstance;
    }
    
    public OkHttpClient getOkHttpClient() {
        return mOkClient;
    }
    
    
    
    public void post() {
        
    }
    
    public void get() {
        
    }
}
