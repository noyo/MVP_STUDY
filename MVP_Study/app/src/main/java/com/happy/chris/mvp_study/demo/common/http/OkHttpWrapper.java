package com.happy.chris.mvp_study.demo.common.http;

import android.support.annotation.NonNull;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * package: com.happy.chris.mvp_study.common.http
 * <p>
 * description:
 * <p>
 * Created by zhouzhaojun on 2017/5/10.
 */

public class OkHttpWrapper {
    
    private final static Byte[] LOCK_BYTE = new Byte[0];
    private static OkHttpWrapper mInstance;
    private OkHttpClient mOkClient;
    
    private OkHttpWrapper() {
        mOkClient = getOkHttpClientBuilder().build();
    }
    
    public static OkHttpWrapper getInstance() {
        if (mInstance == null) {
            synchronized (LOCK_BYTE) {
                if (mInstance == null) {
                    mInstance = new OkHttpWrapper();
                }
            }
        }
        return mInstance;
    }
    
    public OkHttpClient getOkHttpClient() {
        return mOkClient;
    }
    
    /**
     * get OkHttp builder
     * @return Builder
     */
    private OkHttpClient.Builder getOkHttpClientBuilder() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        
        okHttpClientBuilder.cookieJar(new CookieJar() {//cookie
            HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
            
            @Override
            public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }
            
            @Override
            public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        }).hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {//使用https必须进行证书验证， 复写verif方法绕过证书验证
                return true;
            }
        }).connectTimeout(10, TimeUnit.SECONDS)//设置超时
                .readTimeout(10, TimeUnit.SECONDS);
        
        SslVerify.getInstance().setCertificates(okHttpClientBuilder);
        
        return okHttpClientBuilder;
    }
    
    /**
     * 发起网络请求时设置tag，在这可以根据tag取消、中断响应请求
     *
     * @param tag Constants.class.hashCode()
     */
    public void cancelByTag(Object tag) {
        if (mOkClient != null) {
            for (Call call : mOkClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }
    
    /**
     * destroy
     */
    public void onDestroy() {
        if (null != mInstance) {
            synchronized (LOCK_BYTE) {
                mOkClient = null;
                mInstance = null;
            }
        }
    }
    
    /**
     * SSL证书验证
     */
    private static class SslVerify {
        
        private SslVerify() {
            
        }
        
        synchronized static SslVerify getInstance() {
            return new SslVerify();
        }
        
        /**
         * 设置证书
         * @param builder OkHttpClient.Builder
         */
        void setCertificates(OkHttpClient.Builder builder) {
            SSLSocketFactory socketFactory = getSslSocketFactory();
            if (socketFactory == null) {
                socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            }
            if (socketFactory != null) {
                builder.sslSocketFactory(socketFactory);
            }
        }
    
        /**
         * 网站证书验证: 覆盖X509TrustManager，信任所有证书
         *
         * @return SSLSocketFactory
         */
        SSLSocketFactory getSslSocketFactory() {
            TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            }};
            // Install the all-trusting trust manager
            try {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustManagers, new java.security.SecureRandom());
                return sc.getSocketFactory();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
