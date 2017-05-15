package com.happy.chris.mvp_study.demo.common.http;

import okhttp3.Request;

/**
 * package: com.happy.chris.uestcliblogin.common.http
 * company: 安康应急
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/10.
 */

public interface Requester<T> {
    
    /**
     * get
     */
    
    String get(Request r);
    String getAsync(Request r);
    
    
    /**
     * post
     */

    String post();
    String postAsync();
    void post(T t);
    void postAsync(T t);
}
