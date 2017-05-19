package com.happy.chris.mvp_study.demo.common.http;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.Buffer;

import static com.happy.chris.mvp_study.demo.common.http.HttpException.ERROR_HTTP_FAILED;
import static com.happy.chris.mvp_study.demo.common.http.HttpException.ERROR_REQUEST_NULL;

/**
 * package: com.happy.chris.mvp_study.demo.common.http
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/16.
 */

public class HttpChannel {
    private final static String TAG = "HttpChannel";

    private final static HttpChannel mInstance = new HttpChannel();
    private OkHttpClient mOkClient;
    private static BaseCallBack mCurCallBack;

    private HttpChannel() {
        mOkClient = OkHttpWrapper.getInstance().getOkHttpClient();
    }

    /**
     * @return HttpChannel Instance
     */
    public static HttpChannel getInstance() {
        return mInstance;
    }

    /**
     * start http connection
     *
     * @param body     data
     * @return response
     */
    public Object startHttp(HttpBody body) throws HttpException {
        Request request = null;
        Request.Builder builder = new Request.Builder().url(body.url);
        
        /* tag:help to cancel the request */
        builder.tag(body);
        
        /* add header */
        for (String key : body.header.keySet()) {
            builder.addHeader(key, body.header.get(key));
        }
        
        /* start request */
        if (request == null) {
            throw new HttpException(ERROR_REQUEST_NULL);
        }
        switch (body.httpType) {
            case SYNC_POST:
                /* upload file */
                builder.post(body.requestBody);
            case SYNC_GET:
                return doSyncRequest(request);
            case ASYNC_POST:
                /* upload file */
                builder.post(body.requestBody);
            case ASYNC_GET:
                doAsyncRequest(request, body.callBack);
        }
        return null;
    }

    /**
     * connect
     *
     * @param request request body
     */
    private Object doSyncRequest(Request request) throws HttpException {
        try {
            return mOkClient.newCall(request).execute();
        } catch (IOException e) {
            throw new HttpException(e, ERROR_HTTP_FAILED);
        }
    }

    /**
     *
     * @param request  request body
     * @param callback null: synchronization
     *                 not null: asynchronous
     */
    private void doAsyncRequest(Request request, BaseCallBack callback) {
        mOkClient.newCall(request).enqueue(callback);
    }

    private String bodyToString(final Request request) {

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
