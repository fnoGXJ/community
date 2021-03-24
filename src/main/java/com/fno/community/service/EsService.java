package com.fno.community.service;

import com.fno.community.mapper.QuestionMapper;
import com.fno.community.model.Question;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EsService {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    QuestionMapper questionMapper;
    public List<Question> searchByTitle(String title) {

        SearchRequest searchRequest = new SearchRequest("question");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", title);
        builder.query(matchQueryBuilder);
        searchRequest.source(builder);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHit[] hits = search.getHits().getHits();
        return mapToString(hits);
    }
    public List<Question> searchRelatedQuestions(Integer id){
        Question question = questionMapper.selectByPrimaryKey(id);
        List<Question> questions = searchByTitle(question.getTitle());
        List<Question> ret = new ArrayList<>();
        int size = Math.min(5,questions.size());
        for(int i = 0; i < size; i++){
            ret.add(questions.get(i));
        }
        return ret;
    }
    private List<Question> mapToString(SearchHit[] hits){
        List<Question> questions = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> map = hit.getSourceAsMap();
            Question question = new Question();
            question.setCommentCount((Integer) map.get("comment_count"));
            question.setLikeCount((Integer) map.get("like_count"));
            question.setGmtCreate((Long) map.get("gmt_create"));
            question.setViewCount((Integer) map.get("view_count"));
            question.setId((Integer) map.get("id"));
            question.setGmtModified((Long) map.get("gmt_modified"));
            question.setTag((String) map.get("tag"));
            question.setTitle((String) map.get("title"));
            question.setCreator((String) map.get("creator"));
            question.setDescription((String) map.get("description"));
            questions.add(question);
        }
        for (Question question : questions) {
            System.out.println(question);
        }
        return questions;
    }
}
