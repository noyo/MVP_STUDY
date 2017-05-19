package com.happy.chris.mvp_study.demo.common.http;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * package: com.happy.chris.mvp_study.demo.common.http
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/16.
 */

public class HttpCallBack<T> implements BaseCallBack<T> {
    @Override
    @SuppressWarnings("unchecked")
    public T onResponse(Response response) throws IOException {
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            //如果用户写了泛型，就会进入这里，否者不会执行
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type beanType = parameterizedType.getActualTypeArguments()[0];
            if (beanType == String.class) {
                //如果是String类型，直接返回字符串
                return (T) response.body().string();
            } else {
                //如果是 Bean List Map ，则解析完后返回
                return new Gson().fromJson(response.body().string(), beanType);
            }
        } else {
            //如果没有写泛型，直接返回Response对象
            return (T) response;
        }
    }
    
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        
    }
    
    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        
    }
}
