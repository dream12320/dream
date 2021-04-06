package com.serviceImp;

import com.dto.PostType;
import com.mapper.PostTypeMapper;
import com.service.PostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTypeServiceImp implements PostTypeService {
    @Autowired
    PostTypeMapper postTypeMapper;


    @Override
    public PostType findPostTypeById(int id) {
        return postTypeMapper.findPostTypeById(id);
    }

    @Override
    public PostType findPostTypeByName(String name) {
        return postTypeMapper.findPostTypeByName(name);
    }

    @Override
    public List<PostType> findAllPostType() {
        return postTypeMapper.findAllPostType();
    }

    @Override
    public int insertPostType(PostType postType) {
        postTypeMapper.insertPostType(postType);
        return 0;
    }

    @Override
    public int deletePostTypeById(int id) {
        postTypeMapper.deletePostTypeById(id);
        return 0;
    }

    @Override
    public int updatePostType(PostType postType) {
        postTypeMapper.updatePostType(postType);
        return 0;
    }
}
