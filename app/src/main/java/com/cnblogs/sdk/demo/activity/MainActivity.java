package com.cnblogs.sdk.demo.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.demo.R;

/**
 * @author rae
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSdk();
        ViewGroup groupView = findViewById(R.id.ll_content);
        for (int i = 0; i < groupView.getChildCount(); i++) {
            groupView.getChildAt(i).setOnClickListener(this::onItemClick);
        }
    }

    /**
     * 初始化接口，这里为了方便演示，直接在Activity初始化了。
     * 正式部署时请移到{@link Application#onCreate()} 中进行初始化操作。
     */
    private void initSdk() {
        // 初始化
        CnblogsSdk.config(new CnblogsSdk.Builder(getApplication()).debug(true));
    }

    private void onItemClick(View view) {
        String cls = view.getTag().toString();
        Intent intent = new Intent();
        intent.setClassName(getApplicationContext(), cls);
        startActivity(intent);
    }
}