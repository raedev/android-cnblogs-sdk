package com.cnblogs.api;


import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cnblogs.api.param.MomentCommentParam;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by rae on 2019-10-21.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@RunWith(AndroidJUnit4.class)
public class MomentApiTest extends CnblogsApiTest {

    private IMomentApi mApi;

    @Override
    public void setup() {
        super.setup();
        mApi = mOpenApi.getMomentApi();
    }

    @Test
    public void testMomentAll() {
        run(mApi.getMoments(IMomentApi.MOMENT_TYPE_ALL, 1, 20, System.currentTimeMillis()));
    }

    @Test
    public void testMomentMe() {
        run(mApi.getMoments(IMomentApi.MOMENT_TYPE_MY, 1, 20, System.currentTimeMillis()));
    }

    @Test
    public void testMomentFollow() {
        run(mApi.getMoments(IMomentApi.MOMENT_TYPE_FOLLOWING, 1, 20, System.currentTimeMillis()));
    }

    @Test
    public void testReplyMoment() {
        run(mApi.getReplyMoments(1, 20, System.currentTimeMillis()));
    }

    @Test
    public void testAtMeMoment() {
        run(mApi.getAtMeMoments(1, 20, System.currentTimeMillis()));
    }

    @Test
    public void testMomentDetail() {
//        run(mApi.getMomentDetail("catSakura","1731563"));
//        run(mApi.getMomentDetail("haohao111","1731662"));
        run(mApi.getMomentDetail("393130", "1731431"));
    }

    @Test
    public void testPostMoment() {
        MomentCommentParam param = new MomentCommentParam();
        param.ingId = 1733007;
        param.userId = 393130;
        param.parentCommentId = 0;
        param.content = "这是一条很有个性的评论";
        run(mApi.postMomentComment(param));
    }

    @Test
    public void testLuckyStarRanking() {
        run(mApi.getLuckyStarRanking(System.currentTimeMillis()));
    }

}
