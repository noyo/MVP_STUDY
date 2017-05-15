package com.happy.chris.mvp_study.handler;

/**
 * package: com.happy.chris.mvp_study.handler
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/12.
 */

public class CallBack {
    
    public interface LoadDataCallback {
        
        void loadSuccess(Object data);
        
        void loadFailed(Object msg);
    }
}
