package com.fno.community.service;

import com.fno.community.dto.CommentDTO;
import com.fno.community.typeEnum.CommentTypeEnum;
import com.fno.community.exception.CustomizeErrorCode;
import com.fno.community.exception.CustomizeException;
import com.fno.community.mapper.*;
import com.fno.community.model.*;
import com.fno.community.typeEnum.NotificationEnum;
import com.fno.community.typeEnum.NotificationStatusEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentExtMapper commentExtMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationMapper notificationMapper;
    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (parentComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);
            insertNotification(comment,NotificationEnum.REPLY_COMMENT.getType());
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);
            insertNotification(comment,NotificationEnum.REPLY_QUESTION.getType());
        }
    }

    public List<CommentDTO> listByTargetId(Integer id, CommentTypeEnum type) {
        CommentExample example = new CommentExample();
        example.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);
        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //获取评论人
        Set<String> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<String> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        //获取评论人并转换为map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<String,User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(),user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    public void insertNotification(Comment comment, Integer type){
        String replier = null;
        if(type == 1) replier = questionMapper.selectByPrimaryKey(comment.getParentId()).getCreator();
        else if(type == 2) replier = commentMapper.selectByPrimaryKey(comment.getParentId()).getCommentator();
        Notification record = new Notification();
        record.setGmtCreate(System.currentTimeMillis());
        record.setType(type);
        record.setOuterid(comment.getParentId());
        record.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        record.setReceiver(replier);
        record.setNotifier(comment.getCommentator());
        notificationMapper.insert(record);
    }
}
