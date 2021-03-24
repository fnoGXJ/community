package com.fno.community.controller;

import com.fno.community.dto.CommentDTO;
import com.fno.community.typeEnum.CommentTypeEnum;
import com.fno.community.dto.QuestionDTO;
import com.fno.community.model.Question;
import com.fno.community.service.CommentService;
import com.fno.community.service.EsService;
import com.fno.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @Autowired
    EsService esService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTOList);
        questionService.incView(id);
        List<Question> questions = esService.searchRelatedQuestions(id);
        model.addAttribute("relatedQuestions",questions);
        return "question";
    }
}
