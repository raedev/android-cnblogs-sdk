package com.cnblogs.sdk.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cnblogs.sdk.demo.R;
import com.cnblogs.sdk.demo.entity.ApiDetailInfo;
import com.dandan.jsonhandleview.library.JsonViewLayout;

/**
 * @author RAE
 * @date 2021/02/21
 */
public class ApiDetailActivity extends BaseActivity {

    ApiDetailInfo mDetailInfo;

    public static void routeTo(Context context, ApiDetailInfo detailInfo) {
        Intent intent = new Intent(context, ApiDetailActivity.class);
        intent.putExtra("detail", detailInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_detail);
        mDetailInfo = getIntent().getParcelableExtra("detail");
        setTitle(mDetailInfo.getTitle());
        if (mDetailInfo.getJson() != null) {
            JsonViewLayout jsonView = findViewById(R.id.jsonView);
            jsonView.bindJson(mDetailInfo.getJson());
            jsonView.expandAll();
            jsonView.setTextSize(12);
        }
        TextView titleView = findViewById(R.id.tv_title);
        titleView.setText(String.format("%s(%s)", mDetailInfo.getTitle(), mDetailInfo.getName()));
    }
}
