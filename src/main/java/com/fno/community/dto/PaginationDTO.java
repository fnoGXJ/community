package com.fno.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        if(totalCount % size == 0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size+1;
        }
        if(page<1) page = 1;
        if(page>totalPage) page = totalPage;
        this.page = page;
        for(int i = 0; i < 3; i++){
            if(page-i > 0) pages.add(0,page-i);
            if(i!=0 && page+i <= totalPage) pages.add(page+i);
        }
        if(page != 1){
            showPrevious = true;
        }
        if(page != totalPage){
            showNext = true;
        }
        if(!pages.contains(1)){
            showFirstPage = true;
        }
        if(!pages.contains(totalPage)){
            showEndPage = true;
        }
    }
}
