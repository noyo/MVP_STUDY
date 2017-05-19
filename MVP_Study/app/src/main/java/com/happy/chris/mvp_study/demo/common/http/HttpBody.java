package com.happy.chris.mvp_study.demo.common.http;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 * package: com.happy.chris.mvp_study.demo.common.http
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/16.
 */

public class HttpBody {
    public final static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public final static MediaType UPLOAD = MediaType.parse("multipart/form-data; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    
    String url;
    Map<String, String> header = new HashMap<>();
    BaseRequestBody requestBody;
    BaseCallBack callBack;
    HttpType httpType;
    
    enum HttpType {
        SYNC_GET,
        SYNC_POST,
        ASYNC_GET,
        ASYNC_POST
    }
}
