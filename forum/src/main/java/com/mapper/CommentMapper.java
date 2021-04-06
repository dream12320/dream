package com.mapper;

import com.dto.Comment;

import java.util.List;

public interface CommentMapper {

    Comment findCommentById(int id);
    List<Comment> findCommentByPostId(int postId);
    int insertComment(Comment comment);
}
