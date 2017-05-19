package com.happy.chris.mvp_study.demo.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * package: com.happy.chris.mvp_study.demo.common.ui
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/19.
 */

public abstract class BaseActivity extends Activity {
    
    protected Context context;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        context = this;
        initViews();
        initData();
        initEvent();
    }
    
    abstract public int setLayoutId();
    
    public abstract void initViews();
    
    public abstract void initData();
    
    public abstract void initEvent();
}
