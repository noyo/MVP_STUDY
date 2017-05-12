package com.happy.chris.mvp_study;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jzzs.ParentsHelper.App;
import com.jzzs.ParentsHelper.Utils.DataHelper;
import com.jzzs.ParentsHelper.Utils.HttpHelper;
import com.jzzs.ParentsHelper.Utils.NetUtil;
import com.jzzs.ParentsHelper.Utils.Utils;
import com.jzzs.ParentsHelper.beans.FriendViewHoldBean;
import com.jzzs.ParentsHelper.beans.FriendsInfoResponse;
import com.jzzs.ParentsHelper.beans.IMChatGroupModel;
import com.jzzs.ParentsHelper.beans.PostDatas;
import com.jzzs.ParentsHelper.beans.PostDatasModel;
import com.jzzs.ParentsHelper.beans.TestResponse;
import com.jzzs.ParentsHelper.common.Constants;
import com.jzzs.ParentsHelper.common.GloableParams;
import com.jzzs.ParentsHelper.dao.GroupDao;
import com.jzzs.ParentsHelper.entity.User;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import net.sf.json.JSONObject;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * package: com.jzzs.ParentsHelper.emergencylib
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/4/28.
 */

public class GloableParamsUtils {
    
    private static final String TAG = "GloableParamsUtils";
    private static boolean sUserGroupBind = false;
    
    /**
     * 绑定组群和用户信息
     */
    public static void sInitUser2Group() {
        if (sUserGroupBind || GloableParams.UserInfos2 == null || GloableParams.ListGroupInfos4 == null) {
            Log.i(TAG, "sInitUser2Group had been executed");
            return;
        }
        
        
        int userL = GloableParams.UserInfos2.size();
        int groupL = GloableParams.ListGroupInfos4.size();
        for (int i = 0; i < userL; i++) {
            User user = GloableParams.UserInfos2.get(i);
            user.getGroupIds().clear();
            for (int k = 0; k < groupL; k++) {
                List<FriendViewHoldBean> userListByGroup = GroupDao.GetGroupMemberList2(
                        GloableParams.ListGroupInfos4.get(k).getGroupId());
                for (int j = 0; j < userListByGroup.size(); j++) {
                    if (user.getUserId().equals(userListByGroup.get(j).getFriendId())) {
                        if (!user.getGroupIds().contains(userListByGroup.get(j).getGroupId())) {
                            user.getGroupIds().add(userListByGroup.get(j).getGroupId());
                        }
                        break;
                    }
                }
            }
            user.setCounnt(0);
        }
        sUserGroupBind = true;
        Log.i(TAG, "sInitUser2Group success");
    }
    
    /**
     * 加载通讯录中好友成员
     */
    public static void sInitUserInfo2(final Context context) {
        final DbUtils dbUtils = DataHelper.getDbutilsInstance();
        List<User> listUsers;
        try {
            listUsers = dbUtils.findAll(User.class);
            if (null != listUsers) {
                GloableParams.UserInfos2.clear();
                for (int i = 0; i < listUsers.size(); i++) {
                    if (listUsers.get(i).getRegionalAuthority() < Utils
                            .getIntValue(context, Constants.RegionalAuthority)) {
                        GloableParams.UserInfos2.add(listUsers.get(i));
                    }
                    
                }
            } else if (NetUtil.checkNetWork(context)) {
                new Thread(new Runnable() {
                    
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        List<User> users = new ArrayList<User>();
                        users = getFriendList2(context);
                        if (null == users) {
                            return;
                        }
                        for (User user : users) {
                            try {
                                dbUtils.save(user);
                            } catch (DbException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            
                        }
                        
                        GloableParams.UserInfos2.clear();
                        
                        for (int i = 0; i < users.size(); i++) {
                            if (users.get(i).getRegionalAuthority() < Utils
                                    .getIntValue(context,
                                            Constants.RegionalAuthority)) {
                                
                                GloableParams.UserInfos2.add(users.get(i));
                                
                            }
                        }
                    }
                    
                }).start();
                
            } else {
                Utils.showLongToast("无网络且本地无数据");
            }
        } catch (DbException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    
    /**
     * 加载通讯录好友的网络数据
     **/
    private static List<User> getFriendList2(Context context) {
        // TODO Auto-generated method stub
        
        // TODO Auto-generated method stub
        String accessToken = Utils.getValue(context, Constants.AccessToken);
        Log.i("accessToken", accessToken + "");
        PostDatas postDatas = new PostDatas();
        postDatas.setFriendsVersion(0);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        
        String urlNow = "Constants.HttpUrl + \"api/Merge/MergeData?access_token=\"" + accessToken;
        
        String result = HttpHelper.doPost(urlNow, gson.toJson(postDatas));
        Log.i("result", result);
        
        if (result != null) {
            Gson gson1 = new Gson();
            FriendsInfoResponse friendsInfoResponse = gson1.fromJson(result,
                    new TypeToken<FriendsInfoResponse>() {
                    }.getType());
            if (null != friendsInfoResponse) {
                return friendsInfoResponse.getListFriends();
            }
        }
        return null;
        
    }
    
    /**
     * 获取通讯录中的群组里面的数据
     **/
    
    public static void sRefreshMailst(int groupType) {
        // TODO Auto-generated method stub
        
        // TODO Auto-generated method stub
        DbUtils dbUtils = DataHelper.getDbutilsInstance();
        List<IMChatGroupModel> imChatGroupModels = new ArrayList<IMChatGroupModel>();
        try {
            
            imChatGroupModels = dbUtils.findAll(IMChatGroupModel.class);
            
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (null != imChatGroupModels) {
            GloableParams.ListGroupInfos4.clear();
            for (int i = 0; i < imChatGroupModels.size(); i++) {
                
                if (imChatGroupModels.get(i).getCreateType() > groupType) {
                    imChatGroupModels.get(i).setVisble(false);
                    
                    // Log.i("不显示", imChatGroupModel.getName());
                } else {
                    imChatGroupModels.get(i).setVisble(true);
                    // Log.i("显示", imChatGroupModel.getName());
                }
                GloableParams.ListGroupInfos4.add(imChatGroupModels.get(i));
            }
            
        } else if (NetUtil.checkNetWork(App.getInstance())) {
            
            initData2(groupType);
            
        } else {
            Utils.showLongToast("无网络且本地没有数据");
            
        }
        
    }
    
    /**
     * 请求网络数据 ,通讯录群组
     **/
    private static void initData2(final int groupType) {
        // TODO Auto-generated method stub
        
        // TODO Auto-generated method stub
        PostDatasModel postDatasModel = new PostDatasModel();
        HttpUtils httpUtils = new HttpUtils(6 * 1000);
        RequestParams requestParams = new RequestParams();
        JSONObject object = new JSONObject();
        object.element("IMCGV", 0);
        object.element("SCV", 1);
        requestParams.addHeader("Content-type",
                "application/json;charset=UTF-8");
        try {
            requestParams.setBodyEntity(new StringEntity(object.toString(),
                    "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        httpUtils.send(HttpRequest.HttpMethod.POST,
                HttpHelper.GetValidApiURL("api/Merge/MergeData"),
                requestParams, new RequestCallBack<String>() {
                    
                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        
                    }
                    
                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        if (null != arg0.result) {
                            List<IMChatGroupModel> imChatGroupModels;
                            Gson gson = new Gson();
                            TestResponse testResponse = gson.fromJson(
                                    arg0.result, new TypeToken<TestResponse>() {
                                    }.getType());
                            if (null != testResponse) {
                                
                                imChatGroupModels = testResponse
                                        .getIMChatGroupModelList();
                                
                                GloableParams.ListGroupInfos4.clear();
                                if (imChatGroupModels != null) {
                                    for (IMChatGroupModel imChatGroupModel : imChatGroupModels) {
                                        
                                        if (imChatGroupModel.getCreateType() > groupType) {
                                            imChatGroupModel.setVisble(false);
                                            Log.i("不显示",
                                                    imChatGroupModel.getName());
                                        } else {
                                            imChatGroupModel.setVisble(true);
                                        }
                                        GloableParams.ListGroupInfos4
                                                .add(imChatGroupModel);
                                        
                                        try {
                                            DataHelper.getDbutilsInstance()
                                                    .save(imChatGroupModel);
                                        } catch (DbException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        
                                    }
                                    
                                }
                                
                            } else {
                                Utils.showLongToast("服务器解析失败");
                            }
                            
                        } else {
                            Utils.showLongToast("服务器返回数据为空");
                        }
                    }
                });
        
    }
}
