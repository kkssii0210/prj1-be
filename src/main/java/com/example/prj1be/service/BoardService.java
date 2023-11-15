package com.example.prj1be.service;

import com.example.prj1be.domain.Board;
import com.example.prj1be.domain.Member;
import com.example.prj1be.domain.MyDto1;
import com.example.prj1be.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;
    private final MemberService memberService;

    public boolean save(MyDto1 dto, Member login) {
        dto.setWriter(login.getId());
        return mapper.insert(dto) == 1;
    }
    public boolean validate(MyDto1 dto) {
        if (dto == null) {
            return false;
        }
        if (dto.getContent() == null || dto.getContent().isBlank()) {
            return false;
        }
        if (dto.getTitle()==null||dto.getTitle().isBlank()){
            return false;
        }
        return true;
    }

    public List<MyDto1> findAll() {
        return mapper.selectAll();
    }

    public MyDto1 get(Integer id) {
        return mapper.selectById(id);

    }

    public boolean remove(Integer id) {
        return mapper.deleteById(id) == 1;
    }

    public boolean edit(MyDto1 dto) {
        return mapper.updateById(dto) == 1;
    }

    public boolean hasAccess(Integer id, Member login) {
        if (memberService.isAdmin(login)) {
            return true;
        }
        MyDto1 myDto = mapper.selectById(id);
        return myDto.getWriter().equals(login.getId());
    }

}
