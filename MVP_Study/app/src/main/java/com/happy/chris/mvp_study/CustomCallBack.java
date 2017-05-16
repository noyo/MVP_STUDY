package com.happy.chris.mvp_study;

import com.google.myjson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * com.vkei.common.channel.okhttp
 *
 * @author Chris <br/>
 *         创建日期 2016.04.2016/4/21 <br/>
 *         功能:
 *         修改者，修改日期，修改内容.
 *         使用方式:
            client.newCall(request).enqueue(new CustomCallBack<String>() {//指定数据类型
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    mResponse = onResponse(response);//处于UI线程
                }
            });
 */
public class CustomCallBack<T> extends BaseCallBack {
    @Override
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
    public void onFailure(Call call, IOException e) {

    }
}
