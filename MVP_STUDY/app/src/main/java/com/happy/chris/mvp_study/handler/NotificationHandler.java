package com.jzzs.ParentsHelper.handler;

import java.util.ArrayList;

/**
 * package: com.jzzs.ParentsHelper.handler
 * <p>
 * description: notification distribution
 * <p>
 * Created by zhouzhaojun on 2017/5/26.
 */

public class NotificationHandler extends EventHandler<NotificationHandler.NotificationCallBack> {
    
    /* TAG: log info where log info come */
    private final static String TAG = "NotificationHandler";
    /* synchronized lock */
    private final static Byte[] LOCK_SYNC = new Byte[0];
    /* singleton */
    private static NotificationHandler mInstance;
    
    private NotificationHandler() {
        HandlerManager.getInstance().registerHandler(NotificationCallBack.class, this);
    }
    
    public static NotificationHandler getInstance() {
        if (mInstance == null) {
            synchronized (LOCK_SYNC) {
                if (mInstance == null) {
                    mInstance = new NotificationHandler();
                }
            }
        }
        return mInstance;
    }
    
    @Override
    public void handleEvent(NotificationCallBack callBack, Object... objects) {
        checkNullMsgbody(TAG + "handleEvent\\n objects is null", objects);
        if (!(objects[0] instanceof MessageType) || objects.length < 2) return;
        if (mListeners != null && mListeners.contains(callBack)) {
            MessageType msgType = (MessageType) objects[0];
            callBack.receivedNewMsg(msgType, objects[1]);
        }
    }
    
    @Override
    public void handleEvents(Object... objects) {
        checkNullMsgbody(TAG + "handleEvents\\n objects is null", objects);
        if (!(objects[0] instanceof MessageType) || objects.length < 2) return;
        for (NotificationCallBack callBack : mListeners) {
            MessageType msgType = (MessageType) objects[0];
            callBack.receivedNewMsg(msgType, objects[1]);
        }
    }
    
    @Override
    public void callAllBack(Object m, Object obj) {
        checkNullMsgbody(TAG + "handleEvents\\n m is null", m);
        checkNullMsgbody(TAG + "handleEvents\\n m is null", obj);
        for (NotificationCallBack callBack : mListeners) {
            callBack.callBack((MessageType) m, obj);
        }
    }
    
    @Override
    public void register(NotificationCallBack callBack) {
        checkNullCallback(callBack, TAG + ".register: callback can not be null");
        if (mListeners == null) {
            mListeners = new ArrayList<>();
        }
        if (!mListeners.contains(callBack)) {
            mListeners.add(callBack);
        }
    }
    
    @Override
    public void unRegister(NotificationCallBack callBack) {
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
        HandlerManager.getInstance().unregisterHandler(NotificationCallBack.class);
    }
    
    public interface NotificationCallBack extends BaseCallBack {
        void receivedNewMsg(MessageType msgType, Object object);
    }
    
    /**
     * 消息类型
     */
    public enum MessageType {
        SINGLE,
        GROUP,
        NOTICE,
        ILLUSTRATEDMATERIAL
    }
}
