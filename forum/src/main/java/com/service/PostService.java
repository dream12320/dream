package com.service;

import com.dto.Post;

import java.util.List;

public interface PostService {
    Post findPostById(int id);
    List<Post> findPostByTypeId(int typeId);
    List<Post> findPostByUserId(int userId);
    List<Post> findAllPost();
    int insertPost(Post post);
    int updatePostById(Post post);
    int deletePostById(int id);
    int deleteManyPost(int items[]);

    List<Post> findPostByFlowNumMore200();
    List<Post> findPostByFlowNumMore100();
    List<Post> findPostByFlowNumMore50();
    List<Post> findPostByFlowNumMore10();
}
