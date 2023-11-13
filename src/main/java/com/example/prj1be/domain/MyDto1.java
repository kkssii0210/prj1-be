package com.example.prj1be.domain;

import lombok.Data;
import org.apache.ibatis.annotations.Insert;

@Data
public class MyDto1 {
    private Integer id;
    private String title;
    private String content;
    private String writer;
    private String inserted;
}
