package com.cnblogs.sdk.demo.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cnblogs.sdk.demo.ApiListAdapter;
import com.cnblogs.sdk.demo.R;

/**
 * @author RAE
 * @date 2021/02/24
 */
public abstract class ApiListActivity extends BaseActivity {

    final ApiListAdapter mAdapter = new ApiListAdapter(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        onLoadApiItems(mAdapter);
    }

    public abstract void onLoadApiItems(ApiListAdapter adapter);

}
