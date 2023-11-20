package com.example.prj1be.service;

import com.example.prj1be.domain.Comment;
import com.example.prj1be.domain.Member;
import com.example.prj1be.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentMapper mapper;
    public boolean add(Comment comment, Member login) {
        comment.setMemberId(login.getId());
        return mapper.insert(comment) == 1;
    }

    public boolean validate(Comment comment) {
        if (comment == null) {
            return false;
        }
        if (comment.getBoardId() == null || comment.getBoardId()<1){
            return false;
        }
        if (comment.getComment() == null || comment.getComment().isBlank()) {
            return false;
        }
        return true;
    }

    public List<Comment> list(Integer boardId) {
        return mapper.selectByBoardId(boardId);
    }


    public boolean delete(Integer commentId) {
        return mapper.deleteById(commentId) == 1;
    }

    public boolean hasAccess(Integer commentId, Member login) {
        Comment comment = mapper.selectById(commentId);
        return comment.getMemberId().equals(login.getId());
    }
    public boolean update(Comment comment) {
        return mapper.update(comment) == 1;
    }

    public boolean updateValidate(Comment comment) {
        if (comment == null) {
            return false;
        }

        if (comment.getId() == null) {
            return false;
        }

        if (comment.getComment() == null || comment.getComment().isBlank()) {
            return false;
        }

        return true;
    }
}
