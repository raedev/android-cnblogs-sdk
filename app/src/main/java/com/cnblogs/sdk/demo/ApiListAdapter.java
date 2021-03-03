package com.cnblogs.sdk.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnblogs.sdk.demo.activity.ApiDetailActivity;
import com.cnblogs.sdk.demo.entity.ApiDetailInfo;
import com.cnblogs.sdk.internal.CnblogsLogger;
import com.github.raedev.swift.utils.JsonUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author RAE
 * @date 2021/02/24
 */
public final class ApiListAdapter extends RecyclerView.Adapter<ApiListAdapter.ApiViewHolder> {

    private final List<ApiDetailInfo> mDataList = new ArrayList<>();
    /**
     * Demo应用不考虑内存泄漏问题
     */
    private final Activity mActivity;

    public ApiListAdapter(Activity activity) {
        mActivity = activity;
    }

    public void addItem(String title, String name) {
        addItem(title, name, name);
    }

    public void addItem(String title, String name, String methodName) {
        ApiDetailInfo detailInfo = new ApiDetailInfo(title, name, null);
        detailInfo.setTarget(mActivity);
        detailInfo.setMethodName(methodName);
        mDataList.add(detailInfo);
    }

    @NonNull
    @Override
    public ApiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_api_button, parent, false);
        return new ApiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApiViewHolder holder, int position) {
        ApiDetailInfo item = mDataList.get(position);
        holder.fill(item);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ApiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Button mButton;
        private ApiDetailInfo mItem;

        public ApiViewHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.btn_default);
        }


        public void fill(ApiDetailInfo item) {
            mItem = item;
            mButton.setText(item.getTitle());
            mButton.setOnClickListener(this);

        }

        @SuppressWarnings("rawtypes")
        @Override
        public void onClick(View v) {
            // 调用方法
            final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
            progressDialog.setMessage("正在调用：" + mItem.getTitle());
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            Disposable subscribe = Observable.create((ObservableOnSubscribe<ApiDetailInfo>) emitter -> {
                Object target = mItem.getTarget();
                Method method = target.getClass().getDeclaredMethod(mItem.getMethodName());
                Observable invoke = (Observable) method.invoke(target);
                assert invoke != null;
                @SuppressWarnings("unchecked") Observable<ApiDetailInfo> observable = invoke.map(o -> {
                    mItem.setJson(o == null ? null : JsonUtils.toJson(o));
                    return mItem;
                });
                ApiDetailInfo detailInfo = observable.blockingFirst();
                emitter.onNext(detailInfo);
                emitter.onComplete();
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(progressDialog::dismiss)
                    .subscribe(obj -> {
                        Log.d("rae", "调用成功");
                        ApiDetailActivity.routeTo(v.getContext(), obj);
                    }, error -> {
                        String msg = "方法出错：" + mItem.getTitle() + "\r\n" + error.getMessage();
                        CnblogsLogger.e(msg, error);
                        new AlertDialog.Builder(v.getContext())
                                .setMessage(msg)
                                .setPositiveButton("确定", null)
                                .show();

                    });
        }
    }
}
