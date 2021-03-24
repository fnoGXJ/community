package com.fno.community.mapper;

import com.fno.community.model.Question;

public interface QuestionExtMapper {
    void incView(Question question);
    void incComment(Question question);
}