package com.gulei.common.http;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.gulei.common.base.BaseApplication;
import com.gulei.common.utils.Logger;
import com.gulei.common.utils.UrlUtil;
import com.gulei.common.utils.progressmanager.ProgressManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gl152 on 2018/6/13.
 */

public class HttpClient {
    private static HttpClient sInstance;
    private static OkHttpClient okHttpClient;
    private Map<String, Call<ResponseBody>> CALL_MAP = new HashMap<>();
    private Retrofit.Builder builder;


    private HttpClient() {
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(BaseApplication.getContext()));
//        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(Utils.getContext(), R.raw.cer, STORE_PASS, STORE_ALIAS);
        OkHttpClient.Builder OkHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .writeTimeout(10000L, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .cookieJar(cookieJar)
                .addInterceptor(getHttpLoggingInterceptor());
//                .sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager)
//                .hostnameVerifier(HttpsUtils.getHostnameVerifier())

        okHttpClient = ProgressManager.getInstance().with(OkHttpBuilder).build();//传入builder实现进度拦截器添加

        builder = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public static HttpClient getsInstance() {
        if (sInstance == null) {
            synchronized (HttpClient.class) {
                if (sInstance == null) {
                    sInstance = new HttpClient();
                }
            }
        }
        return sInstance;
    }

    public HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.d(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }


    public <T> T createGirlsService(Class<T> serviceClass) {
        Retrofit retrofit = builder.baseUrl(UrlUtil.Girls_BaseUrl).build();
        return retrofit.create(serviceClass);
    }
}
