package com.example.prj1be.service;

import com.example.prj1be.domain.Auth;
import com.example.prj1be.domain.Member;
import com.example.prj1be.mapper.BoardMapper;
import com.example.prj1be.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper mapper;
    private final BoardMapper boardMapper;
    public boolean add(Member member) {
       return mapper.insert(member) == 1;
    }

    public String getId(String id) {
        return mapper.selectId(id);
    }

    public String getEmail(String email) {
        return mapper.selectEmail(email);
    }
    public String getNickname(String nickname) {
        return mapper.selectNickname(nickname);
    }
    public boolean validate(Member member) {
        if (member == null) {
            return false;
        }
        if (member.getEmail().isBlank()) {
            return false;
        }
        if (member.getPassword().isBlank()) {
            return false;
        }
        if (member.getId().isBlank()) {
            return false;
        }
        if (member.getNickname().isBlank()){
            return false;
        }
        return true;
    }

    public List<Member> list() {
        return mapper.selectAll();
    }

    public Member getMember(String id) {
        return mapper.selectById(id);
    }


    public boolean deleteMember(String id) {
        boardMapper.deleteByWriter(id);
        return mapper.deleteById(id) == 1;
    }

    public boolean edit(Member member) {
        return mapper.updateById(member) == 1;
    }

    public boolean login(Member member, WebRequest request) {
        Member dbMember = mapper.selectById(member.getId());

        List<Auth>auth = mapper.selectAuthById(member.getId());
        dbMember.setAuth(auth);
        if (dbMember != null) {
            if (dbMember.getPassword().equals(member.getPassword())){
                dbMember.setPassword("");
                request.setAttribute("login",dbMember, RequestAttributes.SCOPE_SESSION);
                return true;
            }
        }
        return false;
    }

    public boolean hasAccess(String id, Member login) {
        if (isAdmin(login)){
            return true;
        }
        return login.getId().equals(id);
    }
    public boolean isAdmin(Member login) {
        if (login.getAuth() != null) {
            return login.getAuth().stream().map(e -> e.getName())
                    .anyMatch(n->n.equals("admin"));
        }
        return false;
    }
}
