package com.happy.chris.mvp_study.common;

import com.happy.chris.mvp_study.handler.CallBack;
import com.happy.chris.mvp_study.http.OkHttpWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * package: com.jzzs.ParentsHelper.emergencylib.data
 * <p>
 * description: initial
 * <p>
 * Created by zhouzhaojun on 2017/5/12.
 */

public class SourceHelper {
    
    public static void getData(Class<?> c, String tableName, Map urlInfo, CallBack.LoadDataCallback callback) {
        getLocalData(c, tableName, urlInfo, callback);
    }
    
    public static void getLocalData(Class<?> c, String tableName, Map urlInfo, CallBack.LoadDataCallback callback) {
        List<?> datas = null;
        try {
            datas = (List<?>) new Object();//获取本地数据库数据
        } catch (Exception e) {
            // TODO Auto-generated catch block
        } finally {
            if (datas == null || datas.size() <= 0) {
                getNetData(c, tableName, urlInfo, callback);
            } else {
                callback.loadSuccess(datas);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void getNetData(Class<?> c, String tableName, Map urlInfo, CallBack.LoadDataCallback callback) {

        String result = OkHttpWrapper.get(urlInfo);//通过okhttp获取网络数据 params:urlInfo
    
        //保存到本地数据库
        //.....
        List list = new ArrayList<>();
        list.add(result);
        if (new Object().equals(result)) {
            callback.loadSuccess(list);
        } else {
            callback.loadFailed("null");
        }
    }
}
