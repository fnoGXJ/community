package com.fno.community.mapper;

import com.fno.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO QUESTION" +
            "(title,description,gmt_create,gmt_modified,creator,tag) " +
            "VALUES (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
    @Select("SELECT* FROM QUESTION LIMIT #{offset}, #{size}")
    List<Question> list(@Param("offset") Integer offset,@Param("size") Integer size);
    @Select("SELECT COUNT(1) FROM QUESTION")
    Integer count();
}
