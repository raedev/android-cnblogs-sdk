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

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.demo.R;
import com.cnblogs.sdk.CnblogsObserver;
import com.cnblogs.sdk.model.UserInfo;

/**
 * @author rae
 */
public class MainActivity extends AppCompatActivity {

    private Button mUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        },1000);
        initSdk();
        mockLogin();
        startActivity(new Intent(this, BlogApiActivity.class));
        mUserButton = findViewById(R.id.btn_user);
        ViewGroup groupView = findViewById(R.id.ll_content);
        for (int i = 0; i < groupView.getChildCount(); i++) {
            groupView.getChildAt(i).setOnClickListener(this::onItemClick);
        }
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
        CnblogsSdk.config(new CnblogsSdk.Builder(getApplication()).debug(true));
    }

    /**
     * 模拟登录状态信息
     */
    private void mockLogin() {
        String blogCookie = "4D5D860D49C50852298AE903F3007A28DA45EF4D0C642FDE106B87ADA1E00D14299D688C87D3EE3C22F9D18E3B89D0A44F125D5BEEA1342839F98DA849BF81981D2B9A8D3CF06FD58564197322498030F4049BBC";
        String aspNetCookie = "CfDJ8EklyHYHyB5Oj4onWtxTnxZL7ORpVAPZE1s9xB75ECA0JK5ZUm-OTfx4PpTtTKXCGKaZQFe0oaaae0P8HJosKGkLprxhWA6DjJaRXRtCqx-WNmH_Y7FNOAq9PQuty8LnaC4fP8fuFG_zrzG47RtHb3CZm1mzbB3Du4NGP5FCD8xmnXco8i-sXUAJXUXR0X8z0NjC4VTEvtDu3kwYRVwOHoNBCRQltI8rm86zlb4WEHpAz3pWjDIx8DJS2acJ71TvH_ZXfOUEqJgI0HYlUjICz6VdSGqJ5Yre-OENUR5GlqfSIl0y_ryBrnsNz6R6_Hq8aiPAOeNFk0Z2UMFL6JUDnF5aFrALweX9DAdi6P3nBM0bgoiDgXKtQbv5l9ZuRpHctQWVkm6fB5RrQdRAyK-asCm9ASvrSNfBodNTrP7wm6OPW-XtLcipFIPAkZkVZhFUbE6qjEC8P2-rU7Q9A1-A2WsHrI08q_DAo8HB_XZDJ0IUCSsmUsIZIu4ACZ7GeqEtLBO_LoUpEVpx04cMjsnTebqdp55o_rPxlHgg1rdtHmvD-_O0SsJaFoB9ajXU2Zi3SQ";
        CnblogsSdk.getSessionManager().mockLogin(blogCookie, aspNetCookie);
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