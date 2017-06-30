package com.jzzs.ParentsHelper.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * package: com.jzzs.ParentsHelper.handler
 * <p>
 * description: 事件分发管理者
 * <p>
 * Created by zhouzhaojun on 2017/5/27.
 */

public class HandlerManager {
    
    private static final String TAG = "HandlerManager";
    
    private static HandlerManager mInstance = new HandlerManager();
    
    private Map<Class<? extends BaseCallBack>, EventHandler> mEventHandlers;
    
    private HandlerManager() {
        
    }
    
    public static HandlerManager getInstance() {
        return mInstance;
    }
    
    public void registerHandler(Class<? extends BaseCallBack> callBack, EventHandler handler) {
        if (callBack == null) {
            throw new IllegalArgumentException(TAG + ".registerHandler: handler can not be null");
        }
        if (mEventHandlers == null) {
            mEventHandlers = new HashMap<>();
        }
        if (!mEventHandlers.containsKey(callBack)) {
            mEventHandlers.put(callBack, handler);
        }
    }
    
    public void unregisterHandler(Class<? extends BaseCallBack> callBack) {
        if (mEventHandlers != null && mEventHandlers.containsKey(callBack)) {
            mEventHandlers.get(callBack).unRegisterAll();
            mEventHandlers.remove(callBack);
        }
    }
    
    public void unregisterAllHandler() {
        if (mEventHandlers != null && mEventHandlers.size() > 0) {
            for (Class<? extends  BaseCallBack> callback : mEventHandlers.keySet()) {
                mEventHandlers.get(callback).onDestroy();
            }
            mEventHandlers.clear();
        }
    }
    
    public void onDestroy() {
        unregisterAllHandler();
        mEventHandlers = null;
    }
    
}
