package com.happy.chris.mvp_study.demo.common.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * package: com.happy.chris.mvp_study.demo.common.http
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/19.
 */

public class BaseRequestBody extends RequestBody {
    @Nullable
    @Override
    public MediaType contentType() {
        return null;
    }
    
    @Override
    public void writeTo(@NonNull BufferedSink bufferedSink) throws IOException {
        
    }
}
