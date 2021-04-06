package com.dto;

import java.util.ArrayList;
import java.util.List;

public class Personal {
    List<Post> postList=new ArrayList<>();

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }
}
