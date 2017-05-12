package com.happy.chris.mvp_study;

import com.jzzs.ParentsHelper.Utils.DataHelper;
import com.jzzs.ParentsHelper.beans.IMChatGroupModel;
import com.jzzs.ParentsHelper.entity.User;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * package: com.jzzs.ParentsHelper.emergencylib
 * company: 安康应急
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/2.
 */

public class DbUtilsHelper {
    
    /**
     * 根据名字子串搜索本地数据库
     *
     * @param subName 名字子串
     * @return
     */
    public static List<User> sSearchUsersBySubName(String subName) {
        List<User> searchResultList = null;
        DbUtils dbUtils = DataHelper.getDbutilsInstance();
        try {
            searchResultList = dbUtils.findAll(Selector.from(User.class)
                    .where("nickname", "like", "%" + subName + "%")
                    .or("remark", "like", "%" + subName + "%"));
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return searchResultList;
    }
    
    /**
     * 根据查找字符串搜索本地Group
     * @param content 查找关键串
     * @return
     */
    public static List<IMChatGroupModel> searchGroupsByContent(String content) {
        List<IMChatGroupModel> searchResultList = null;
        DbUtils dbUtils = DataHelper.getDbutilsInstance();
        try {
            searchResultList = dbUtils.findAll(Selector.from(IMChatGroupModel.class).where(
                    "Name", "like", "%" + content + "%"));
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return searchResultList;
    }
}
