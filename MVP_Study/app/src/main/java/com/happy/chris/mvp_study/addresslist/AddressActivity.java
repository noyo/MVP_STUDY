package com.happy.chris.mvp_study.addresslist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

/**
 * package: com.happy.chris.mvp_study.addresslist
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/12.
 */

public class AddressActivity extends Activity implements AddressContract.View {
    
    AddressContract.Presenter mPresenter;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FrameLayout(this));
        
        setPresenter(new AddressPresenter(this, this));
    }
    
    @Override
    public void setPresenter(AddressContract.Presenter presenter) {
        mPresenter = presenter;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
