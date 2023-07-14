package com.example.simpleblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type;

    private String keyword;

    public String[] getTypes(){
        if(type == null || type.isEmpty()){
            return null;
        }
        return type.split("");
    }

    public Pageable getPageable(){
        return PageRequest.of(this.page - 1, this.size, Sort.by("bno").descending());
    }

    //편의성을 위해 링크 만드는 함수 제작
    private String link;

    public String getLink(){
        if(link == null){
            StringBuilder builder = new StringBuilder();
            builder.append("page="+this.page);
            builder.append("&size="+this.size);

            if(type != null && type.length() > 0){
                builder.append("&type="+type);
            }

            if(keyword != null){
                try{
                    builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                }catch (UnsupportedEncodingException e){}
            }

            link = builder.toString();
        }

        return link;
    }
}