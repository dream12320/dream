package com.serviceImp;

import com.dto.Post;
import com.mapper.PostMapper;
import com.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImp implements PostService {
    @Autowired
    PostMapper postMapper;

    @Override
    public Post findPostById(int id) {
        return postMapper.findPostById(id);
    }

    @Override
    public List<Post> findPostByTypeId(int typeId) {
        return postMapper.findPostByTypeId(typeId);
    }

    @Override
    public List<Post> findPostByUserId(int userId) {
        return postMapper.findPostByUserId(userId);
    }

    @Override
    public List<Post> findAllPost() {
        return postMapper.findAllPost();
    }

    @Override
    public int insertPost(Post post) {
        postMapper.insertPost(post);
        return 0;
    }

    @Override
    public int updatePostById(Post post) {
        postMapper.updatePostById(post);
        return 0;
    }

    @Override
    public int deletePostById(int id) {
        postMapper.deletePostById(id);
        return 0;
    }

    @Override
    public int deleteManyPost(int[] items) {
        postMapper.deleteManyPost(items);
        return 0;
    }

    @Override
    public List<Post> findPostByFlowNumMore200() {
        return postMapper.findPostByFlowNumMore200();
    }

    @Override
    public List<Post> findPostByFlowNumMore100() {
        return postMapper.findPostByFlowNumMore100();
    }

    @Override
    public List<Post> findPostByFlowNumMore50() {
        return postMapper.findPostByFlowNumMore50();
    }

    @Override
    public List<Post> findPostByFlowNumMore10() {
        return postMapper.findPostByFlowNumMore10();
    }
}
