package com.jzzs.ParentsHelper.handler;

import java.util.ArrayList;

/**
 * package: com.jzzs.ParentsHelper.handler
 * <p>
 * description: message distribution
 * <p>
 * Created by zhouzhaojun on 2017/5/26.
 */

public class MsgDispatchHandler extends EventHandler<MsgDispatchHandler.MsgDispatchCallBack> {
    
    /* TAG: log info where log info come */
    private final static String TAG = "MsgDispatchHandler";
    /* synchronized lock */
    private final static Byte[] LOCK_SYNC = new Byte[0];
    /* singleton */
    private static MsgDispatchHandler mInstance;
    
    private MsgDispatchHandler() {
        HandlerManager.getInstance().registerHandler(MsgDispatchCallBack.class, this);
    }
    
    public static MsgDispatchHandler getInstance() {
        if (mInstance == null) {
            synchronized (LOCK_SYNC) {
                if (mInstance == null) {
                    mInstance = new MsgDispatchHandler();
                }
            }
        }
        return mInstance;
    }
    
    @Override
    public void handleEvent(MsgDispatchCallBack callBack, Object... objects) {
        checkNullMsgbody(TAG + "handleEvent\\n objects is null", objects);
        if (mListeners != null && mListeners.contains(callBack)) {
            callBack.handleMessage(Object.class, objects);
        }
    }
    
    @Override
    public void handleEvents(Class<?> type, Object... objects) {
        checkNullMsgbody(TAG + "handleEvents\\n objects is null", objects);
        for (MsgDispatchCallBack callBack : mListeners) {
            callBack.handleMessage(type, objects);
        }
    }
    
    @Override
    public void register(MsgDispatchCallBack callBack) {
        checkNullCallback(callBack, TAG + ".register: callback can not be null");
        if (mListeners == null) {
            mListeners = new ArrayList<>();
        }
        if (!mListeners.contains(callBack)) {
            mListeners.add(callBack);
        }
    }
    
    @Override
    public void unRegister(MsgDispatchCallBack callBack) {
        if (mListeners.contains(callBack)) {
            mListeners.remove(callBack);
        }
    }
    
    @Override
    public void unRegisterAll() {
        if (mListeners != null && mListeners.size() > 0) {
            mListeners.clear();
        }
        mListeners = null;
    }
    
    @Override
    public void onDestroy() {
        HandlerManager.getInstance().unregisterHandler(MsgDispatchCallBack.class);
    }
    
    public interface MsgDispatchCallBack extends BaseCallBack {
        void handleMessage(Class<?> type, Object... object);
    }
}
