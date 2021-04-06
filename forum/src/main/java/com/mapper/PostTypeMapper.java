package com.mapper;

import com.dto.PostType;

import java.util.List;

public interface PostTypeMapper {

    PostType findPostTypeById(int id);
    PostType findPostTypeByName(String name);
    List<PostType> findAllPostType();
    int insertPostType(PostType postType);
    int deletePostTypeById(int id);
    int updatePostType(PostType postType);
}
