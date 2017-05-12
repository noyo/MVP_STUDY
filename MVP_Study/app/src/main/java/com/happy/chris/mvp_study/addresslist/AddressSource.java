package com.happy.chris.mvp_study.addresslist;

import android.annotation.SuppressLint;

import com.happy.chris.mvp_study.bean.Person;
import com.happy.chris.mvp_study.common.BaseSource;
import com.happy.chris.mvp_study.common.SourceHelper;
import com.happy.chris.mvp_study.handler.CallBack;

import java.util.HashMap;

/**
 * package: com.jzzs.ParentsHelper.emergencylib.addresslist
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/11.
 */

public class AddressSource extends BaseSource<Integer, Person> {
    /** synchrolock for unique quote of this class */
    private final static Byte[] LOCK = new Byte[0];
    /** unique quote */
    private static AddressSource mInstance;
    
    @SuppressLint("UseSparseArrays")
    private AddressSource() {
        mData = new HashMap<>();
    }
    
    public static AddressSource getInstance() {
        if (mInstance == null) {
            synchronized (LOCK) {
                if (mInstance == null) {
                    mInstance = new AddressSource();
                }
            }
        }
        return mInstance;
    }
    
    /**
     * obtain data from local or net
     *
     * param update:true-regain data from local or net, false-return the data already existed
     *
     * @return AddressSource
     */
    @SuppressWarnings("unchecked")
    public AddressSource getData(final CallBack.LoadDataCallback callBack, boolean update) {
        if (mData.size() <= 0 || update) {
            SourceHelper.getData(Person.class, "table_name", new HashMap<String, String>(),
                    new CallBack.LoadDataCallback() {
                        @Override
                        public void loadSuccess(Object source) {
                            callBack.loadSuccess(source);
                        }
    
                        @Override
                        public void loadFailed(Object msg) {
                            callBack.loadFailed(msg);
                        }
                    });
        }
        return mInstance;
    }
}
