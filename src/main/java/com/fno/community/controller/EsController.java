package com.fno.community.controller;

import com.fno.community.dto.PaginationDTO;
import com.fno.community.service.EsService;
import com.fno.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class EsController {
    @Autowired
    QuestionService questionService;
    @Autowired
    EsService esService;
    @PostMapping("/es")
    public String search(@RequestParam("title") String title,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "size", defaultValue = "5") Integer size,
                         Model model,
                         HttpServletRequest request){
        request.getSession().setAttribute("title",title);
        PaginationDTO pagination = questionService.list(page, size, title);
        model.addAttribute("pagination",pagination);
        return "espage";
    }
    @GetMapping("/es")
    public String turnPage(@RequestParam(name = "page", defaultValue = "1") Integer page,
                           @RequestParam(name = "size", defaultValue = "5") Integer size,
                           Model model,
                           HttpServletRequest request){
        String title = (String) request.getSession().getAttribute("title");
        PaginationDTO pagination = questionService.list(page, size, title);
        model.addAttribute("pagination",pagination);
        return "espage";
    }

}
