package com.jzzs.ParentsHelper.handler;

import java.util.List;

/**
 * package: com.jzzs.ParentsHelper.handler
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/27.
 */

abstract class EventHandler<T extends BaseCallBack> implements Register<T> {
    
    /* distribution colloction */
    List<T> mListeners;
    
    public abstract void onDestroy();
    
    @Override
    public void handleEvent(T callback, Object... objects) {
        
    }
    
    @Override
    public void handleEvents(Object... objects) {
        
    }
    
    @Override
    public void handleEvents(Class<?> type, Object... objects) {
    }
    
    public void callAllBack(Object m, Object obj) {
        
    }
    
    void checkNullMsgbody(String errMsg, Object... objects) {
        if (objects == null || objects.length <= 0) {
            throw new IllegalArgumentException(errMsg);
        }
    }
    
    void checkNullCallback(T callback, String errMsg) {
        if (callback == null) {
            throw new IllegalArgumentException(errMsg);
        }
    }
}
