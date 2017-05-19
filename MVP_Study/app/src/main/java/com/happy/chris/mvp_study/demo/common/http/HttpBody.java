package com.happy.chris.mvp_study.demo.common.http;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

/**
 * package: com.happy.chris.mvp_study.demo.common.http
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/16.
 */

public class HttpBody {
    String url;
    Map<String, String> header = new HashMap<>();
    Map<String, String> body = new HashMap<>();
    HttpType httpType;
    
    enum HttpType {
        UPLOAD,
        POST_JSON,
        POST_MEDIA,
        POST_FORM,
        POST_MULTI,
        GET,
        
    }
}
