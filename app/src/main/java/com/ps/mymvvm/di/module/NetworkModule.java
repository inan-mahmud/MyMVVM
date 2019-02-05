package com.ps.mymvvm.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.ps.mymvvm.data.rest.GithubService;
import com.ps.mymvvm.util.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
//@Singleton
@Module(includes = ViewModelModule.class)
public class NetworkModule {

    @Singleton
    @Provides
    static Gson context() {
        return new Gson();
    }

    @Singleton
    @Provides
    static Cache cache(Context context) {
        return new Cache(new File(context.getCacheDir(), Constants.HTTP_CACHE_DIR),
                Constants.HTTP_CACHE_SIZE);
    }

    @Singleton
    @Provides
    static OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }

    @Singleton
    @Provides
    static HttpLoggingInterceptor okHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                message -> Timber.d(message));
        return logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    static Retrofit retrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Singleton
    @Provides
    static Picasso picasso(Context context, OkHttpClient okHttpClient) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }

    @Singleton
    @Provides
    static GithubService githubService(Retrofit retrofit) {
        return retrofit.create(GithubService.class);
    }
}
