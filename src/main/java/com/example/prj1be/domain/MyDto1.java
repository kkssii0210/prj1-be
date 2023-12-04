package com.example.prj1be.domain;

import com.example.prj1be.util.AppUtil;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Data
public class MyDto1 {
    private Integer id;
    private String title;
    private String content;
    private String writer;
    private String nickname;
    private LocalDateTime inserted;
    private Integer countLike;
    private Integer countComment;
    private LocalDateTime ago;
    public String getAgo() {
        return AppUtil.getAgo(inserted);
    }

//    public String getAgo() {
//        LocalDateTime now = LocalDateTime.now();
//        if (inserted.isBefore(now.minusYears(1))){
//            Period between = Period.between(inserted.toLocalDate(), now.toLocalDate());
//            return between.get(ChronoUnit.YEARS) +"년 전";
//        } else if (inserted.isBefore(now.minusMonths(1))) {
//            Period between = Period.between(inserted.toLocalDate(), now.toLocalDate());
//            return between.get(ChronoUnit.MONTHS)+ "달 전";
//        }else if (inserted.isBefore(now.minusDays(1))){
//            Period between = Period.between(inserted.toLocalDate(), now.toLocalDate());
//            return between.get(ChronoUnit.DAYS)+"일 전";
//        }else if (inserted.isBefore(now.minusHours(1))){
//            Duration between = Duration.between(inserted, now);
//            return between.get(ChronoUnit.HOURS)+"시간 전";
//        }else {
//            Duration between = Duration.between(inserted, now);
//            return between.get(ChronoUnit.MINUTES)+"분 전";
//        }
//
//    }
}
