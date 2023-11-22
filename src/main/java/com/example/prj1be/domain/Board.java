package com.example.prj1be.domain;

import com.example.prj1be.util.AppUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Board {
    private Integer id;
    private String title;
    private String content;
    private String writer;
    private String nickName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime inserted;
    private Integer countComment;
    private Integer countLike;
    private List<BoardFile> files;
    private Integer countFile;
    private String ago;

    public String getAgo() {
        return AppUtil.getAgo(inserted, LocalDateTime.now());
    }


}
