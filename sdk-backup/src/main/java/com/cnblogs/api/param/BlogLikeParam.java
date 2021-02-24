package com.cnblogs.api.param;


public class BlogLikeParam {
    private int postId;
    private boolean isAbandoned;
    private String voteType = "Digg";

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public boolean isAbandoned() {
        return isAbandoned;
    }

    public void setAbandoned(boolean abandoned) {
        isAbandoned = abandoned;
    }

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }
}
