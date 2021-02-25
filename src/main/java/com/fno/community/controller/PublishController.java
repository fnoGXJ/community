package com.fno.community.controller;

import com.fno.community.mapper.QuestionMapper;
import com.fno.community.mapper.UserMapper;
import com.fno.community.model.Question;
import com.fno.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(HttpServletRequest request){
        request.getSession().removeAttribute("error");
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model){
        Cookie[] cookies = request.getCookies();
        User user = null;
        for(Cookie cookie : cookies){
            if("token".equals(cookie.getName())){
                String token = cookie.getValue();
                user = userMapper.findByAccountId(token);
            }
        }
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "/publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getAccountId());

        model.addAttribute("title",title.trim());
        model.addAttribute("description",description.trim());
        model.addAttribute("tag",tag.trim());
        if("".equals(title.trim())){
            model.addAttribute("error","标题不能为空");
            return "/publish";
        }
        if("".equals(description.trim())){
            model.addAttribute("error","问题补充不能为空");
            return "/publish";
        }

        questionMapper.create(question);
        return "redirect:/";
    }
}
