package com.happy.chris.mvp_study.addresslist;

import android.content.Context;

import com.happy.chris.mvp_study.common.Repository;
import com.happy.chris.mvp_study.handler.CallBack;

/**
 * package: com.jzzs.ParentsHelper.emergencylib.addresslist
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/10.
 */

public class AddressPresenter implements AddressContract.Presenter {
    
    AddressContract.View view;
    Repository repository;
    
    public AddressPresenter(Context context, AddressContract.View view) {
        repository = AddressRepository.getInstance(context);
        
//        view.setPresenter(this);
    }
    
    @Override
    public void start() {
        loadSource(false);
    }
    
    @Override
    public void loadSource(boolean update) {
        repository.getSource(mLoadCallBack, update);
    }
    
    private CallBack.LoadDataCallback mLoadCallBack = new CallBack.LoadDataCallback() {
        @Override
        public void loadSuccess(Object source) {
            //数据处理
            //显示
        }

        @Override
        public void loadFailed(Object msg) {
            
        }
    };
}
