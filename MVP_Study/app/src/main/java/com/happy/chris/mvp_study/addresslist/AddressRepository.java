package com.happy.chris.mvp_study.addresslist;

import android.content.Context;

import com.happy.chris.mvp_study.common.Repository;
import com.happy.chris.mvp_study.handler.CallBack;

/**
 * package: com.jzzs.ParentsHelper.emergencylib.addresslist
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/11.
 */

public class AddressRepository extends Repository<AddressSource> {
    
    private final static Byte[] LOCK = new Byte[0];
    
    private static AddressRepository mInstance;
    private Context mContext;
    
    private AddressRepository(Context context) {
        mContext = context;
        mSource = AddressSource.getInstance();
    }
    
    public static AddressRepository getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCK) {
                if (mInstance == null) {
                    mInstance = new AddressRepository(context);
                }
            }
        }
        return mInstance;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void getSource(CallBack.LoadDataCallback callBack, boolean update) {
        mSource.getData(callBack, update);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mInstance != null) {
            mInstance = null;
        }
    }
}
