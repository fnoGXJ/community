package com.fno.community.model.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class QuestionEs {
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String title;
}
