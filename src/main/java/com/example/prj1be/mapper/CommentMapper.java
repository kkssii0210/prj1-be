package com.example.prj1be.mapper;

import com.example.prj1be.domain.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("""
INSERT INTO comment (boardId,comment,memberId)
VALUES (#{boardId},#{comment},#{memberId})
""")
    int insert(Comment comment);

    @Select("""
SELECT *
FROM comment
WHERE boardId = #{boardId}
""")
    List<Comment> selectByBoardId(Integer boardId);

    @Delete("""
DELETE FROM comment
WHERE id=#{commentId}
""")
    int deleteById(Integer commentId);


    @Select("""
SELECT * FROM comment
WHERE id=#{commentId}
""")
    Comment selectById(Integer commentId);
}
