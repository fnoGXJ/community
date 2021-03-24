package com.fno.community;

import com.alibaba.fastjson.JSON;
import com.fno.community.model.Question;
import com.fno.community.model.User;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jnlp.ClipboardService;
import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyTest {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Test
    public void test01() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("hhh");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.index());
    }

    @Test
    public void test02() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("question1");
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);

    }
    @Test
    public void test03() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("hhh");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }
    @Test
    public void test04() throws IOException {
        User user = new User();
        user.setBio("我是郭潇健的儿子");
        user.setAvatarUrl("sanjkncx");
        IndexRequest indexRequest = new IndexRequest("hhh");
        indexRequest.id("2");
        indexRequest.timeout("1s");
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index.status());
    }
    @Test
    public void test05() throws IOException {
        GetRequest getRequest = new GetRequest("question","11");
        GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(documentFields.getSourceAsString());
    }

    @Test
    public void  test06() throws IOException {

        SearchRequest searchRequest = new SearchRequest("question");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "王俊凯");
        builder.query(matchQueryBuilder);
        searchRequest.source(builder);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Question> questions = new ArrayList<>();
        SearchHit[] hits = search.getHits().getHits();
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
    }
}
