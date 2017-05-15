package com.happy.chris.mvp_study.addresslist;

import com.happy.chris.mvp_study.common.BasePresenter;
import com.happy.chris.mvp_study.common.BaseView;

/**
 * package: com.jzzs.ParentsHelper.emergencylib.addresslis
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/10.
 */

public class AddressContract {
    
    interface View extends BaseView<Presenter> {
        
    }
    
    interface Presenter extends BasePresenter {
        void loadSource(boolean update);
    }
}
