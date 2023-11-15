package com.example.prj1be.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Member {
    private String id;
    private String password;
    private String email;
    private LocalDateTime inserted;
    private String nickname;
    private List<Auth> auth;
}
