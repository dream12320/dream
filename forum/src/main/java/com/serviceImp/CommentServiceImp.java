package com.serviceImp;

import com.dto.Comment;
import com.mapper.CommentMapper;
import com.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    CommentMapper commentMapper;


    @Override
    public Comment findCommentById(int id) {
        return commentMapper.findCommentById(id);
    }

    @Override
    public List<Comment> findCommentByPostId(int postId) {
        return commentMapper.findCommentByPostId(postId);
    }

    @Override
    public int insertComment(Comment comment) {
        commentMapper.insertComment(comment);
        return 0;
    }
}
