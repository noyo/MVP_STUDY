package com.happy.chris.mvp_study.demo.common.http;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

/**
 * package: com.happy.chris.mvp_study.demo.common.http
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/17.
 */

public interface BaseCallBack<T> extends Callback {
    
    T onResponse(Response response) throws IOException;
}
