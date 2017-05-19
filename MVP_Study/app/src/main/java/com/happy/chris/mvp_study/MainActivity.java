package com.happy.chris.mvp_study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.happy.chris.mvp_study.demo.common.http.BaseCallBack;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements BaseCallBack<String> {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public String onResponse(Response response) {
        return null;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) {

    }
}
