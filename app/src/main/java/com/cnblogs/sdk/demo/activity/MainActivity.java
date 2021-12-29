package com.cnblogs.sdk.demo.activity;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.cnblogs.sdk.CnblogsObserver;
import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.demo.R;
import com.cnblogs.sdk.http.Composer;
import com.cnblogs.sdk.internal.CnblogsLogger;
import com.cnblogs.sdk.model.ProfileInfo;
import com.cnblogs.sdk.model.UserInfo;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author rae
 */
public class MainActivity extends AppCompatActivity {

    private Button mUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        }, 1000);
        initSdk();
        mockLogin();
        mUserButton = findViewById(R.id.btn_user);
        ViewGroup groupView = findViewById(R.id.ll_content);
        for (int i = 0; i < groupView.getChildCount(); i++) {
            groupView.getChildAt(i).setOnClickListener(this::onItemClick);
        }

        // 测试
        Observable.create(emitter -> {
            long start = System.currentTimeMillis();
            ProfileInfo info = CnblogsSdk.getInstance().getDataProvider().getUserDataApi().queryProfileInfo("chenrui7").blockingFirst();
            CnblogsLogger.d("个人信息：" + info.getDisplayName() + "，耗时：" + (System.currentTimeMillis() - start));
        }).compose(Composer.uiThread(this)).subscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserState();
    }

    private void updateUserState() {
        UserInfo userInfo = CnblogsSdk.getSessionManager().getUserInfo();
        mUserButton.setText(userInfo == null ? "官网登录接口(未登录)" : "官网登录接口(" + userInfo.getDisplayName() + ")");
    }

    /**
     * 初始化接口，这里为了方便演示，直接在Activity初始化了。
     * 正式部署时请移到{@link Application#onCreate()} 中进行初始化操作。
     */
    private void initSdk() {
        // 初始化
        CnblogsSdk.init(new CnblogsSdk.Builder(getApplication()).debug(true));
    }

    /**
     * 模拟登录状态信息
     */
    private void mockLogin() {
        String aspNetCookie = "CfDJ8NACB8VE9qlHm6Ujjqxvg5C-YR0Lcx1ekRQNVz2_mKUmL9CIUiPXFuoxs4y88KxsEYiLrIn7g_vodUInfC3zjSYOjGe0WCT9hyY2zLFsnkh9u25oBohuRHtdhrHKxMk7t9y2wRxYhXPO3LntOuRYhzzaNoc020HEaO27BhUhiPafQgGVh9tdC2pYulyIpvCI5_Y9-8xOnmlF3kru36v6rvGJ2jd432AEYja_X61p3gCbqU6cG-ONzOvqhzsEmMIxfAeNAENxkyontXZMlBpi5qGfut8iClrKwceaOCFpEdns7lQiRfGutjM-kAHhqfc6TkgjVaLXDbKbSsS4-wOEHuynESDl6-A6rL07vtkZZSMmipBwMsTzDYqKL_Djz8BfPR5hIX7ZeE_Q0qJlMqvIGEyouKar_BmPRwdp0GuOAjLFOwKBUPjQjT55Kfmi3bOd5390tsLCOhl6fQ3clezZk9I1mQf1BZJdetCEO1KsYwtG8aAiqKYRuJC_EkBznEu2ubrcK1NzeOMdmVwIZs2f9766aAquGfLgdtmhHM7qJSmZdwGLcP41yXyjTZolH-Vh7Q";
        CnblogsSdk.getSessionManager().mockLogin(aspNetCookie);
        CnblogsSdk.getInstance()
                .getDataProvider()
                .getUserDataApi()
                .queryUserInfo()
                .subscribe(new CnblogsObserver<UserInfo>() {
                    @Override
                    public void onAccept(@NonNull UserInfo userInfo) {
                        updateUserState();
                    }
                });


    }

    private void onItemClick(View view) {
        String cls = view.getTag().toString();
        Intent intent = new Intent();
        intent.setClassName(getApplicationContext(), cls);
        startActivity(intent);
    }
}