package com.example.prj1be.mapper;

import com.example.prj1be.domain.Board;
import com.example.prj1be.domain.MyDto1;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {
    @Insert("""
INSERT INTO board (title, content, writer)
VALUES (#{title},#{content},#{writer})
""")
    int insert(MyDto1 dto);

    @Select("""
SELECT b.id,b.title,m.nickname,b.inserted,b.writer
FROM board b JOIN member m ON b.writer = m.id
ORDER BY b.id desc
""")
    List<MyDto1> selectAll();
@Select("""
SELECT b.id,b.title,m.id writer,b.inserted,b.content,m.nickname
FROM board b JOIN member m ON b.writer = m.id
WHERE b.id = #{id}
""")
    MyDto1 selectById(Integer id);
@Delete("""
DELETE FROM board
WHERE id = #{id}
""")
    int deleteById(Integer id);
@Update("""
UPDATE board
SET title=#{title},
content=#{content},
writer=#{writer}
WHERE id=#{id}
""")
    int updateById(MyDto1 dto);

@Delete("""
DELETE FROM board
WHERE writer = #{writer}
""")
int deleteByWriter(String writer);
}
