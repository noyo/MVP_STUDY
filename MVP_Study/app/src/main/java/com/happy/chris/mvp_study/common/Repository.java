package com.happy.chris.mvp_study.common;

import com.happy.chris.mvp_study.handler.CallBack;

/**
 * package: com.jzzs.ParentsHelper.emergencylib
 * <p>
 * description: 仓库接口， method of manipulation about source
 * <p>
 * Created by zhouzhaojun on 2017/5/11.
 */

public abstract class Repository<T extends BaseSource> {
    
    private final static Byte[] LOCK = new Byte[0];
    
    protected T mSource;
    
    public abstract void getSource(CallBack.LoadDataCallback callBack, boolean update);
    
    public void onDestroy() {
        mSource.clear();
    }
}
