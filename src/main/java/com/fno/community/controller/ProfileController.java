package com.fno.community.controller;

import com.fno.community.dto.PaginationDTO;
import com.fno.community.mapper.UserMapper;
import com.fno.community.model.Notification;
import com.fno.community.model.User;
import com.fno.community.service.NotificationService;
import com.fno.community.service.QuestionService;
import com.fno.community.typeEnum.NotificationStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.IconUIResource;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size){
        Cookie[] cookies = request.getCookies();
        String creator = null;
        for (Cookie cookie : cookies) {
            if("token".equals(cookie.getName())){
                creator = cookie.getValue();
                break;
            }
        }
        List<Notification> notifications = notificationService.listUnread(creator);
        Integer count = notificationService.count(creator);
        model.addAttribute("replyCount", count);
        model.addAttribute("replies",notifications);
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO pagination = questionService.list(creator, page, size);
            model.addAttribute("pagination",pagination);
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        return "profile";
    }
}
