package com.fno.community.service;

import com.fno.community.mapper.NotificationMapper;
import com.fno.community.model.Notification;
import com.fno.community.model.NotificationExample;
import com.fno.community.typeEnum.NotificationStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    public List<Notification> listUnread(String user){
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(user);
        List<Notification> notifications = notificationMapper.selectByExample(example);
        return notifications;
    }

    public Integer count(String user) {
        NotificationExample example = new NotificationExample();
        example.createCriteria()
            .andReceiverEqualTo(user)
            .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        long l = notificationMapper.countByExample(example);
        return (int) l;
    }
}
