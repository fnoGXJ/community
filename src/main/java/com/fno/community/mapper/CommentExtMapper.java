package com.fno.community.mapper;

import com.fno.community.model.Comment;

public interface CommentExtMapper {
    void incCommentCount(Comment comment);
}
