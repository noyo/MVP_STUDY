package com.happy.chris.mvp_study;

import android.content.Context;

import com.jzzs.ParentsHelper.beans.IMChatGroupModel;
import com.jzzs.ParentsHelper.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * package: com.jzzs.ParentsHelper.emergencylib
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/2.
 */

public abstract class ChooseRrBaseAdapter<T> extends BaseAdapter<T> {
    
    protected static List<User> mUserChoosed = new ArrayList<User>();//用户选中列表
    protected static List<IMChatGroupModel> mGroupChoosed = new ArrayList<IMChatGroupModel>();//组群选中列表
    private static List<BaseAdapter> mList;
    
    public ChooseRrBaseAdapter(Context context) {
        this(context, null);
    }
    
    public ChooseRrBaseAdapter(Context context, List<T> objects) {
        super(context, objects);
        register(this);
    }
    
    public void clear() {
        mUserChoosed.clear();
        mGroupChoosed.clear();
        if (mList != null && mList.size() > 0) {
            for (BaseAdapter adapter : mList) {
                adapter.notifyDataSetChanged();
            }
        }
    }
    
    public void register(BaseAdapter adapter) {
        if (mList == null) {
            mList = new ArrayList<BaseAdapter>();
        }
        mList.add(adapter);
    }
    
    public void unRegisterAll() {
        if (mList != null) {
            mList.clear();
            mList = null;
        }
    }
}
