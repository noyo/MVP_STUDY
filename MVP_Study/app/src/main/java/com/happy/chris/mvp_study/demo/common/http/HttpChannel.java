package com.happy.chris.mvp_study.demo.common.http;

import android.support.annotation.NonNull;
import android.util.Log;

import com.happy.chris.mvp_study.demo.common.util.log.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * package: com.happy.chris.mvp_study.demo.common.http
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/16.
 */

public class HttpChannel {
    private final static String TAG = "HttpChannel";
    private final static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static MediaType UPLOAD = MediaType.parse("multipart/form-data; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    
    private final static HttpChannel mInstance = new HttpChannel();
    private OkHttpClient mOkClient;
    private static List<BaseCallBack> mHttpListeners;
    
    private HttpChannel() {
        mOkClient = OkHttpWrapper.getInstance().getOkHttpClient();
    }

    /**
     *
     * @param callBack register
     * @return HttpChannel Instance
     */
    public static HttpChannel getInstance(BaseCallBack callBack) {
        if (mHttpListeners == null) {
            mHttpListeners = new ArrayList<>();
        }
        if (!mHttpListeners.contains(callBack)) {
            mHttpListeners.add(callBack);
        }
        return mInstance;
    }

    public void unregister(BaseCallBack callBack) {
        if (mHttpListeners.contains(callBack)) {
            mHttpListeners.remove(callBack);
        }
    }

    public void unregisterAll() {
        if (mHttpListeners != null) {
            mHttpListeners.clear();
            mHttpListeners = null;
        }
    }
    
    /**
     * start http connection
     * @param body data
     * @return response
     */
    public String startHttp(HttpBody body) {
        String response = null;
        HttpBody.HttpType type = body.httpType;
        switch (type) {
            case GET:
                break;
            case POST_JSON:
                break;
            case UPLOAD:
                break;
            case POST_MEDIA:
                break;
            case POST_FORM:
                LogUtils.I(TAG, "POST_FORM: this function had not yet been kept");
                break;
            case POST_MULTI:
                LogUtils.I(TAG, "POST_MULTI: this function had not yet been kept");
                break;
        }
        
        return response;
    }
    
    /**
     * connect
     * @param request request body
     */
    private void doRequest(final Request request){
        
        mOkClient.newCall(request).enqueue(new HttpCallBack() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                for (BaseCallBack callBack : mHttpListeners) {
                    callBack.onFailure(call, e);
                }
            }
            
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                for (BaseCallBack callBack : mHttpListeners) {
                    callBack.onResponse(call, response);
                }
            }
        });
    }
    
    private String httpPost(HttpBody body) throws HttpException {
        try {
            RequestBody body;
            if (null != context.mFiles) {
                body = getUploadFileRequestBody(context);
            } else {
                body = getHttpRequestBody(context);
            }
            Request request = new Request.Builder()
                    .tag(this)
                    .url(url)
                    .post(body)
                    .addHeader("User-Agent", "vkei")
                    .build();
            LogUtil.d(TAG, "request body = " + bodyToString(request));
            Response response = client.newCall(request).execute();
            String responseContent;
            if (response.isSuccessful()) {
                responseContent = response.body().string();
            } else {
                throw new HttpException(response.code());
            }
            response.body().close();
            return responseContent;
        } catch (Exception e) {
            throw new HttpException(e, ErrorCode.ERR_NETWORK_BROKEN);
        }
    }
    
    private int parseResposne(CmdContext context, String responseStr) {
        Msg response = JsonHelper.parseJson(responseStr, Msg.class);
        if (response == null) {
            return ErrorCode.ERR_DATA_WRONG;
        }
        RspMsgHead msgHead = JsonHelper.parseJson(response.getHead(), RspMsgHead.class);
        if (msgHead == null) {
            return ErrorCode.ERR_DATA_WRONG;
        }
        
        Log.i(TAG, msgHead.toString());
        context.setRspHead(msgHead);
        if (msgHead.errorcode != 0) {
            return msgHead.errorcode;
        }
        
        String cmd = msgHead.getCmd();
        
        context.setRspBody(JsonHelper.parseJson(response.getBody(), context.getRspClass()));

        /*.............*/
//        if (context.getRspBody() == null) {
//            return ErrorCode.ERR_DATA_WRONG;
//        }
        
        return 0;
    }
    
    private Msg getRequestMsg(CmdContext context) {
        Msg request = new Msg();
        request.setHead(JsonHelper.serialObject(getMsgHead(context)));
        request.setBody(JsonHelper.serialObject(context.getRequest()));
        return request;
    }
    
    private Map<String, String> getUploadMsg(CmdContext context) {
        Map<String, String> head = Utils.Obj2Map(getMsgHead(context));
        Map<String, String> body = Utils.Obj2Map(context.getRequest());
        if (null != head) {
            head.putAll(body);
        }
        return head;
    }
    
    private MsgHead getMsgHead(CmdContext context) {
        
        ThirdUserInfo userInfo = mApp.getUserInfo();
        MsgHead msgHead = new MsgHead();
        msgHead.lang = DeviceUtil.getLang(mApp.getContext());
        msgHead.appid = mApp.getProductCode();
        msgHead.uid = userInfo == null ? "" : userInfo.uid;
        msgHead.auth_key = userInfo == null ? "" : userInfo.auth_key;
//        msgHead.model = mApp.getModel();
        msgHead.imei = mApp.getImei();
        msgHead.cmd = context.getCmd();
        msgHead.seq = context.getSeq();
        msgHead.sid = mApp.getSession().getVid();
        msgHead.csig = Helper.getSign(mApp.getSignString(), DeviceUtil.getDeviceId(mApp));
        msgHead.version = Utils.getAppVersionName(mApp.getContext());
        msgHead.versioncode = Utils.getAppVersionCode(mApp.getContext());
        msgHead.network = NetworkUtil.getNetWorkType(mApp.getContext());
        return msgHead;
    }
    
    /**
     * 获取上传文件RequestBody
     * @param context
     * @return
     */
    private RequestBody getUploadFileRequestBody(CmdContext context) {
        List<String> files = context.getFiles();
        
        RequestBody body = null;
        MultipartBody.Builder builder = new MultipartBody.Builder();
        
        Map<String, String> entity = getUploadMsg(context);
        if (null != entity && entity.size() > 0) {
            for (Map.Entry<String, String> en : entity.entrySet()) {
                builder.addFormDataPart(en.getKey(), en.getValue());
            }
        }
        
        if (null != files && files.size() > 0) {
            for (int i = 0; i < files.size(); i++) {
                File file = new File(files.get(i));
                builder.addFormDataPart("file["+i+"]", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
            }
        }
        
        body = builder.setType(UPLOAD).build();
        
        return body;
    }
    
    /**
     * 获取普通http请求RequestBody
     * @param context
     * @return
     */
    private RequestBody getHttpRequestBody(CmdContext context) {
        RequestBody body = null;
        Msg request = getRequestMsg(context);
        String entity = JsonHelper.serialObject(request);
        LogUtil.d(TAG, "Request:" + body);
        return RequestBody.create(JSON, entity);
    }
    
    
    
    private String bodyToString(final Request request){
        
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
