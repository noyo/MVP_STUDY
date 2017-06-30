package com.jzzs.ParentsHelper.handler;

/**
 * package: com.jzzs.ParentsHelper.handler
 * <p>
 * description: M-message O-body
 * <p>
 * Created by zhouzhaojun on 2017/5/26.
 */

public interface BaseCallBack<M, O> {

    void callBack(M msg, O obj);
}
