package com.jzzs.ParentsHelper.handler;

/**
 * package: com.jzzs.ParentsHelper.handler
 * <p>
 * description: 消息分发-功能
 * <p>
 * Created by zhouzhaojun on 2017/5/26.
 */

public interface Register<T extends BaseCallBack> {
    
    /**
     * 消息分发
     *
     * @param callback 消息接收对象
     * @param objects 消息体
     */
    void handleEvent(T callback, Object... objects);
    /**
     * 消息分发
     *
     * @param objects 消息体
     */
    void handleEvents(Object... objects);
    /**
     * 消息分发
     *
     * @param type 消息接收者类型
     * @param objects 消息体
     */
    void handleEvents(Class<?> type, Object... objects);
    /**
     * 添加监听
     * @param callback 监听对象
     */
    void register(T callback);
    /**
     * 取消监听
     * @param callback 监听对象
     */
    void unRegister(T callback);
    /**
     * 取消所有监听
     */
    void unRegisterAll();
}
