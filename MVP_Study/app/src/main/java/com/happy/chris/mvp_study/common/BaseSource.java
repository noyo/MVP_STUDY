package com.happy.chris.mvp_study.common;

import java.util.Map;

/**
 * package: com.jzzs.ParentsHelper.emergencylib.data
 * <p>
 * description: 数据基类
 * <p>
 * Created by zhouzhaojun on 2017/5/11.
 */

public abstract class BaseSource<K, V> {
    /** unique identifier of the source */
    protected String mId;
    /** what is source name */
    protected String mName;
    /** introduce this source */
    protected String mDescription;
    /** what source contain */
    protected Map<K, V> mData;
    
    public void clear() {
        if (mData != null){
            mData.clear();
        }
    }
    
    @Override
    public String toString() {
        return "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mData=" + mData;
    }
    
    public String getId() {
        return mId;
    }
    
    public void setId(String mId) {
        this.mId = mId;
    }
    
    public String getName() {
        return mName;
    }
    
    public void setName(String mName) {
        this.mName = mName;
    }
    
    public String getDescription() {
        return mDescription;
    }
    
    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
