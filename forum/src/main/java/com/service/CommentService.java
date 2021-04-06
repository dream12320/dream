package com.service;

import com.dto.Comment;

import java.util.List;

public interface CommentService {
    Comment findCommentById(int id);
    List<Comment> findCommentByPostId(int postId);
    int insertComment(Comment comment);
}
