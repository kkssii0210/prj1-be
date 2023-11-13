package com.example.prj1be.service;

import com.example.prj1be.domain.Board;
import com.example.prj1be.domain.MyDto1;
import com.example.prj1be.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;

    public boolean save(Board board) {
        return mapper.insert(board) == 1;
    }
    public boolean validate(Board board) {
        if (board == null) {
            return false;
        }
        if (board.getContent() == null || board.getContent().isBlank()) {
            return false;
        }
        if (board.getTitle()==null||board.getTitle().isBlank()){
            return false;
        }
        if (board.getWriter()==null||board.getWriter().isBlank()){
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
}
